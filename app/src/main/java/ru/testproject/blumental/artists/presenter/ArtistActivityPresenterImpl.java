package ru.testproject.blumental.artists.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import javax.inject.Inject;

import ru.testproject.blumental.artists.model.Model;
import ru.testproject.blumental.artists.model.Utils;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.view.View;
import ru.testproject.blumental.artists.view.activity.ArtistListActivity;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public class ArtistActivityPresenterImpl extends BasePresenter implements ArtistActivityPresenter {

    @Inject
    Model model;

    ArtistListActivity view;

    @Override
    public void loadFirstPage() {
        List<Artist> artists = view.getPage(0);
        Subscription pageDownloadSubscription = model.downloadPage(view.getContext(), artists)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        view.stopProgress();
                        view.refresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });

        addSubscription(pageDownloadSubscription);
    }

    @Override
    public void loadNextPage(int offset) {
        final List<Artist> artists = view.getPage(offset);
        Subscription pageDownloadSubscription = model.downloadPage(view.getContext(), artists)
                .subscribe(new Subscriber<Integer>() {
                    int newElementsCount = 0;

                    @Override
                    public void onCompleted() {
                        view.onNewPageLoaded(newElementsCount);
                        view.refresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        newElementsCount++;
                    }
                });

        addSubscription(pageDownloadSubscription);
    }

    @Override
    public Bitmap getThumbnailBitmap(int id) {
        try {
            File thumbnail = Utils.getThumbnail(view.getContext(), id);
            return BitmapFactory.decodeFile(thumbnail.getAbsolutePath());
        } catch (MalformedURLException e) {
            view.showToast(e.toString());
        }
        return null;
    }

    @Override
    public void onCreate(View view) {
        App.getComponent().inject(this);
        this.view = (ArtistListActivity) view;
    }

    @Override
    public void onResume() {
        //load JSON with artist list
        view.showProgress();
        loadArtistList();
    }

    @Override
    public void loadArtistList() {
        Subscription subscription = model.downloadArtistList()
                .subscribe(new Subscriber<List<Artist>>() {
                    @Override
                    public void onCompleted() {
                        loadFirstPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.toString());
                    }

                    @Override
                    public void onNext(List<Artist> artists) {
                        view.showArtists(artists);
                    }
                });

        addSubscription(subscription);
    }
}
