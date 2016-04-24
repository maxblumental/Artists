package ru.testproject.blumental.artists.other;

import android.app.Application;

import ru.testproject.blumental.artists.other.di.AppComponent;
import ru.testproject.blumental.artists.other.di.DaggerAppComponent;
import ru.testproject.blumental.artists.other.di.ModelModule;

/**
 * Application class.
 * It deals with DI and provides
 * a static component field for all
 * classes having dependencies.
 * <p/>
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

        long ramSize = getRamSize();

        component = DaggerAppComponent.builder()
                .modelModule(new ModelModule(ramSize))
                .build();
    }

    private long getRamSize() {
        return Runtime.getRuntime().maxMemory();
    }
}
