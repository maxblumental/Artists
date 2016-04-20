package ru.testproject.blumental.artists.presenter;

import javax.inject.Inject;

import ru.testproject.blumental.artists.view.View;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
abstract public class BasePresenter implements Presenter {

    @Inject
    protected CompositeSubscription compositeSubscription;

    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void onStop() {
        compositeSubscription.clear();
    }

    @Override
    public void onDestroy() {

    }
}
