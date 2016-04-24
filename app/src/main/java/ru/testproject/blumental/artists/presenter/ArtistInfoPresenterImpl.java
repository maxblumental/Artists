package ru.testproject.blumental.artists.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import javax.inject.Inject;

import ru.testproject.blumental.artists.model.Model;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.view.View;
import ru.testproject.blumental.artists.view.activity.ArtistInfoView;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Maxim Blumental on 09/04/16.
 * bvmaks@gmail.com
 */
public class ArtistInfoPresenterImpl extends BasePresenter implements ArtistInfoPresenter {

    @Inject
    Model model;

    private ArtistInfoView view;

    @Override

    public void getCoverBitmap(Artist artist) {
        Context context = view.getContext();

        Subscription subscription = model.downloadCover(context, artist)
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.toString());
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        view.showCover(bitmap);
                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void onCreate(Context context) {
        App.getComponent().inject(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void setView(View view) {
        this.view = (ArtistInfoView) view;
    }
}
