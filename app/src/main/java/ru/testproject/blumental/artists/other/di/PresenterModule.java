package ru.testproject.blumental.artists.other.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.testproject.blumental.artists.model.Model;
import ru.testproject.blumental.artists.model.ModelImpl;
import rx.subscriptions.CompositeSubscription;

/**
 * Module for injecting dependencies
 * of Presenter layer classes of MVP.
 * <p/>
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
@Module
public class PresenterModule {

    @Provides
    CompositeSubscription getCompositeSubscription() {
        return new CompositeSubscription();
    }

    @Provides
    @Singleton
    Model getModel() {
        return new ModelImpl();
    }
}
