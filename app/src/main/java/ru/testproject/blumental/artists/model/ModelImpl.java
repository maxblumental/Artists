package ru.testproject.blumental.artists.model;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.model.data.ArtistDTO;
import ru.testproject.blumental.artists.model.data.ArtistProvider;
import ru.testproject.blumental.artists.model.data.db.ArtistTable;
import ru.testproject.blumental.artists.other.App;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

/**
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
public class ModelImpl implements Model {

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
    public Observable<Void> downloadPage(final Context context, List<ArtistDTO> artistDTOs) {
        return Observable.from(artistDTOs).map(new Func1<ArtistDTO, Void>() {
            @Override
            public Void call(ArtistDTO artistDTO) {
                try {
                    URL url = new URL(artistDTO.getSmallCoverUrl());
                    File smallCoverFile =
                            Utils.getSmallCoverFile(context, artistDTO.getSmallCoverUrl());
                    FileUtils.copyURLToFile(url, smallCoverFile);
                } catch (IOException e) {
                    Exceptions.propagate(e);
                }
                return null;
            }
        })
                .subscribeOn(uiScheduler)
                .observeOn(ioScheduler);
    }

    @Override
    public Observable<Void> fetchData(final Context context) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                String json = Utils.getStringFromAssetsFile(context, "artists.json");
                Gson gson = new Gson();
                Artist[] artists = gson.fromJson(json, Artist[].class);

                for (Artist artist : artists) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ArtistTable.COLUMN_ALBUM_NUMBER, artist.getAlbumNumber());
                    contentValues.put(ArtistTable.COLUMN_TRACK_NUMBER, artist.getTrackNumber());
                    contentValues.put(ArtistTable.COLUMN_ARTIST_NAME, artist.getName());
                    contentValues.put(ArtistTable.COLUMN_ID, artist.getId());
                    contentValues.put(ArtistTable.COLUMN_BIOGRAPHY, artist.getDescription());
                    contentValues.put(ArtistTable.COLUMN_COVER_URL, artist.getCover().getBig());
                    contentValues.put(ArtistTable.COLUMN_SMALL_COVER_URL, artist.getCover().getSmall());
                    contentValues.put(ArtistTable.COLUMN_GENRES, Arrays.toString(artist.getGenres()));

                    context.getContentResolver().insert(ArtistProvider.CONTENT_URI, contentValues);
                }

                subscriber.onCompleted();
            }
        });
    }
}
