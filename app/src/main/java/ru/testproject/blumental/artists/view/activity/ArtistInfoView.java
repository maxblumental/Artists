package ru.testproject.blumental.artists.view.activity;

import android.graphics.Bitmap;

import ru.testproject.blumental.artists.view.View;

/**
 * Specific methods of ArtistInfoActivity
 * as a View in the MVP pattern.
 * <p/>
 * Created by Maxim Blumental on 09/04/16.
 * bvmaks@gmail.com
 */
public interface ArtistInfoView extends View {
    void showCover(Bitmap bitmap);
}
