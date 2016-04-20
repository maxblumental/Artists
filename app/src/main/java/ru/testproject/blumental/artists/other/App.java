package ru.testproject.blumental.artists.other;

import android.app.Application;
import android.os.Handler;

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

        Handler handler = new Handler();

        component = DaggerAppComponent.builder()
                .modelModule(new ModelModule(handler))
                .build();
    }
}
