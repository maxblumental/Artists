package ru.testproject.blumental.artists.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.LruCache;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.exceptions.Exceptions;

/**
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
public class ModelImpl implements Model {

    public final static String ARTIST_LIST_JSON_URL = "http://cache-default02g.cdn.yandex.net/" +
            "download.cdn.yandex.net/mobilization-2016/artists.json";

    public ModelImpl() {
        App.getComponent().inject(this);
    }

    @Inject
    @Named("Cover cache")
    LruCache<String, Bitmap> coverCache;

    @Inject
    @Named("IO Scheduler")
    Scheduler ioScheduler;

    @Inject
    @Named("UI Scheduler")
    Scheduler uiScheduler;

    @Inject
    ThumbnailDownloader thumbnailDownloader;

    @Override
    public void getThumbnail(ArtistListAdapter.ViewHolder holder, String url) {
        thumbnailDownloader.queueThumbnail(holder, url);
    }

    @Override
    public void initThumbnailDownloader(ThumbnailDownloader.DownloadListener listener) {
        if (thumbnailDownloader.getState() != Thread.State.NEW) {
            Handler handler = new Handler();
            thumbnailDownloader = new ThumbnailDownloader(handler);
        }
        thumbnailDownloader.setListener(listener);
        thumbnailDownloader.start();
        thumbnailDownloader.getLooper();
    }

    @Override
    public void stopThumbnailDownloader() {
        thumbnailDownloader.clearMessageQueue();
    }

    @Override
    public Observable<List<Artist>> downloadArtistList() {
        return Observable.create(new Observable.OnSubscribe<List<Artist>>() {
            @Override
            public void call(Subscriber<? super List<Artist>> subscriber) {
                try {
                    URL url = new URL(ARTIST_LIST_JSON_URL);
                    InputStream inputStream = url.openStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    int len;
                    byte[] buffer = new byte[1024];

                    while ((len = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                    }

                    String json = outputStream.toString();

                    Gson gson = new Gson();
                    Artist[] artists = gson.fromJson(json, Artist[].class);
                    List<Artist> listOfArtists = new ArrayList<>(Arrays.asList(artists));

                    subscriber.onNext(listOfArtists);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    Exceptions.propagate(e);
                }
            }
        })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @Override
    public Observable<Bitmap> downloadCover(final Context context, final Artist artist) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                try {
                    URL url = new URL(artist.getCover().getBig());
                    Bitmap bitmap = coverCache.get(url.toString());

                    if (bitmap == null) {
                        bitmap = Utils.downloadCover(url);
                        coverCache.put(url.toString(), bitmap);
                    }

                    subscriber.onNext(bitmap);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    Exceptions.propagate(e);
                }
            }
        })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }
}
