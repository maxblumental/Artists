package ru.testproject.blumental.artists.other.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenterImpl;
import ru.testproject.blumental.artists.presenter.ArtistInfoPresenter;
import ru.testproject.blumental.artists.presenter.ArtistInfoPresenterImpl;

/**
 * Module for injecting dependencies
 * of View layer classes of MVP.
 * <p/>
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
@Module
public class ViewModule {
    @Provides
    @Singleton
    ArtistActivityPresenter getArtistActivityPresenter() {
        return new ArtistActivityPresenterImpl();
    }

    @Provides
    @Singleton
    ArtistInfoPresenter getArtistInfoPresenter() {
        return new ArtistInfoPresenterImpl();
    }
}
