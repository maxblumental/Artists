package ru.testproject.blumental.artists.other;

import android.app.ActivityManager;
import android.app.Application;
import android.os.Build;

import ru.testproject.blumental.artists.other.di.AppComponent;
import ru.testproject.blumental.artists.other.di.DaggerAppComponent;
import ru.testproject.blumental.artists.other.di.ModelModule;

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

        long ramSize = getRamSize();
        component = DaggerAppComponent.builder()
                .modelModule(new ModelModule(ramSize))
                .build();
    }

    private long getRamSize() {
        return Runtime.getRuntime().maxMemory();
    }
}