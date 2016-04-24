package ru.testproject.blumental.artists.other.di;

import android.graphics.Bitmap;
import android.util.LruCache;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.testproject.blumental.artists.model.ThumbnailDownloader;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Module for injecting dependencies
 * of Model layer classes of MVP.
 * <p/>
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
@Module
public class ModelModule {

    // for cache size evaluation
    private long totalRamSize;

    public ModelModule(long totalRamSize) {
        this.totalRamSize = totalRamSize;
    }

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

    /**
     * We make overall cache size to be
     * 1/8 of the total RAM available to our app.
     * We devote 3/4 of this share to small covers.
     */
    @Provides
    @Named("Small cover cache")
    @Singleton
    LruCache<String, Bitmap> getSmallCoverCache() {
        return new LruCache<String, Bitmap>((int) (totalRamSize * 3 / 32)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * We make overall cache size to be
     * 1/8 of the total RAM available to our app.
     * We devote 1/4 of this share to big covers.
     */
    @Provides
    @Named("Cover cache")
    @Singleton
    LruCache<String, Bitmap> getCoverCache() {
        return new LruCache<String, Bitmap>((int) (totalRamSize / 32)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Provides
    ThumbnailDownloader getDownloader() {
        return new ThumbnailDownloader();
    }
}
