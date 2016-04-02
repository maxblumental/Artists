package ru.testproject.blumental.artists.view.activity;

import android.database.Cursor;

import ru.testproject.blumental.artists.view.View;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface ArtistListView extends View {
    void showArtists(Cursor cursor);

    void showProgress();

    void stopProgress();

    void onNewPageLoaded(int newElementsCount);
}
