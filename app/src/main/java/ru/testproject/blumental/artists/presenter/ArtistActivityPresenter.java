package ru.testproject.blumental.artists.presenter;

import android.graphics.Bitmap;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public interface ArtistActivityPresenter extends Presenter {
    void loadFirstPage();

    void loadNextPage(int offset);

    Bitmap getThumbnailBitmap(int id);
}
