package ru.testproject.blumental.artists.other.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenterImpl;

/**
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
}
