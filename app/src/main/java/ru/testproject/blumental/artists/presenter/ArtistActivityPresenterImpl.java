package ru.testproject.blumental.artists.presenter;

import android.graphics.Bitmap;

import java.util.List;

import javax.inject.Inject;

import ru.testproject.blumental.artists.model.Model;
import ru.testproject.blumental.artists.model.ThumbnailDownloader;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.view.View;
import ru.testproject.blumental.artists.view.activity.ArtistListActivity;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public class ArtistActivityPresenterImpl extends BasePresenter implements ArtistActivityPresenter {

    @Inject
    Model model;

    private ArtistListActivity view;

    public void setView(View view) {
        this.view = (ArtistListActivity) view;
    }

    @Override
    public void onCreate() {
        App.getComponent().inject(this);
        model.initThumbnailDownloader(new ThumbnailDownloader.DownloadListener() {
            @Override
            public void onThumbnailDownloaded(ArtistListAdapter.ViewHolder target, Bitmap bitmap) {
                target.setThumbnail(bitmap);
            }
        });
    }

    @Override
    public void onResume() {
        if (view.needElements()) {
            loadArtistList();
        }
    }

    @Override
    public void getBitmap(ArtistListAdapter.ViewHolder holder, String url) {
        model.getThumbnail(holder, url);
    }

    @Override
    public void loadArtistList() {
        view.showProgress();
        Subscription subscription = model.downloadArtistList()
                .subscribe(new Subscriber<List<Artist>>() {
                    @Override
                    public void onCompleted() {
                        view.stopProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showNoInternetScreen();
                    }

                    @Override
                    public void onNext(List<Artist> artists) {
                        view.showArtists(artists);
                        view.refresh();
                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void onDestroy() {
        model.stopThumbnailDownloader();
    }
}
