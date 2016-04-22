package ru.testproject.blumental.artists.presenter;

import android.content.Context;

import ru.testproject.blumental.artists.view.View;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface Presenter {
    void onCreate(Context context);
    void onResume();
    void onStop();
    void onDestroy();
    void setView(View view);
}
