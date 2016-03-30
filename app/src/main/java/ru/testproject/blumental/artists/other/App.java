package ru.testproject.blumental.artists.other;

import android.app.Application;

import ru.testproject.blumental.artists.other.di.AppComponent;
import ru.testproject.blumental.artists.other.di.DaggerAppComponent;

/**
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
public class App extends Application {

    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder().build();
    }
}
