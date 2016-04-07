package ru.testproject.blumental.artists.presenter;

import ru.testproject.blumental.artists.view.View;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface Presenter {
    void onCreate(View view);
    void onResume();
    void onStop();
}
