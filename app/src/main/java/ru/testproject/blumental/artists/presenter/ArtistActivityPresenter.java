package ru.testproject.blumental.artists.presenter;

import android.database.Cursor;
import android.graphics.Bitmap;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public interface ArtistActivityPresenter extends Presenter {
    boolean isSmallCoverLoaded(Cursor cursor);
    void loadNextPage(Cursor cursor);
    Bitmap getSmallCoverBitmap(String url);
    void fetchData();
}
