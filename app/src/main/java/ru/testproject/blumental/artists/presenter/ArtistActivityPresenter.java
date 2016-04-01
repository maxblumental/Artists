package ru.testproject.blumental.artists.presenter;

import android.database.Cursor;
import android.graphics.Bitmap;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public interface ArtistActivityPresenter extends Presenter {
    boolean isSmallCoverLoaded(int id);
    void loadNextPage(Cursor cursor);
    Bitmap getSmallCoverBitmap(int id);
    void fetchData();
    void refresh();
}
