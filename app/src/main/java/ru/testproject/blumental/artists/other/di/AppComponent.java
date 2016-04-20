package ru.testproject.blumental.artists.other.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.testproject.blumental.artists.model.ModelImpl;
import ru.testproject.blumental.artists.model.ThumbnailDownloader;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenterImpl;
import ru.testproject.blumental.artists.presenter.ArtistInfoPresenterImpl;
import ru.testproject.blumental.artists.presenter.BasePresenter;
import ru.testproject.blumental.artists.view.activity.ArtistInfoActivity;
import ru.testproject.blumental.artists.view.activity.ArtistListActivity;

/**
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
@Singleton
public interface AppComponent {

    void inject(ModelImpl model);

    void inject(BasePresenter presenter);

    void inject(ArtistActivityPresenterImpl presenter);

    void inject(ArtistListActivity activity);

    void inject(ArtistInfoActivity activity);

    void inject(ArtistInfoPresenterImpl presenter);

    void inject(ThumbnailDownloader thumbnailDownloader);
}
