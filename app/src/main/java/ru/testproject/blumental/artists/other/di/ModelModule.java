package ru.testproject.blumental.artists.other.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
@Module
public class ModelModule {
    @Provides
    @Named("IO Scheduler")
    @Singleton
    Scheduler getIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Named("UI Scheduler")
    @Singleton
    Scheduler getUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
