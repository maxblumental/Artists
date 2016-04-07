package ru.testproject.blumental.artists.model;

import android.content.Context;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.testproject.blumental.artists.model.data.ArtistDTO;
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
    public Observable<Integer> downloadPage(final Context context, final List<ArtistDTO> artistDTOs) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (ArtistDTO artistDTO : artistDTOs) {
                    try {
                        URL url = new URL(artistDTO.getSmallCoverUrl());
                        File smallCoverFile =
                                Utils.getThumbnail(context, artistDTO.getArtistId());
                        FileUtils.copyURLToFile(url, smallCoverFile);
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
}
