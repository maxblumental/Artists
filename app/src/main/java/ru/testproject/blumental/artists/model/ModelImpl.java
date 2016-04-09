package ru.testproject.blumental.artists.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
    @Named("IO Scheduler")
    Scheduler ioScheduler;

    @Inject
    @Named("UI Scheduler")
    Scheduler uiScheduler;

    @Override
    public Observable<Integer> downloadPage(final Context context, final List<Artist> artists) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (Artist artist : artists) {
                    try {
                        URL url = new URL(artist.getCover().getSmall());
                        File thumbnail =
                                Utils.getThumbnail(context, artist.getId());
                        FileUtils.copyURLToFile(url, thumbnail);
                        subscriber.onNext(1);
                    } catch (IOException e) {
                        Exceptions.propagate(e);
                    }
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
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
                    File coverFile =
                            Utils.getCoverFile(context, artist.getId());
                    FileUtils.copyURLToFile(url, coverFile);
                    subscriber.onNext(BitmapFactory.decodeFile(coverFile.getAbsolutePath()));
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
