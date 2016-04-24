package ru.testproject.blumental.artists.presenter;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Base Presenter class handling
 * composite rx subscriptions.
 * <p/>
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
abstract public class BasePresenter implements Presenter {

    public BasePresenter() {
        Log.e("MVP", getClass().getSimpleName());
    }

    @Inject
    protected CompositeSubscription compositeSubscription;

    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void onCreate(Context context) {
        Log.i("MVP " + getClass().getSimpleName(), "====");
        Log.i("MVP " + getClass().getSimpleName(), "onCreate()");
    }

    @Override
    public void onResume() {
        Log.i("MVP " + getClass().getSimpleName(), "onResume()");
    }

    @Override
    public void onStop() {
        Log.i("MVP " + getClass().getSimpleName(), "onStop()");
        compositeSubscription.clear();
    }

    @Override
    public void onDestroy(boolean isFinishing) {
        Log.i("MVP " + getClass().getSimpleName(), "onDestroy()");
    }
}
