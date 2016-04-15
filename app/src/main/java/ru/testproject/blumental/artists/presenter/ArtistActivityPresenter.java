package ru.testproject.blumental.artists.presenter;

import android.graphics.Bitmap;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public interface ArtistActivityPresenter extends Presenter {
    /**
     * Get bitmap from the url.
     * Returns the bitmap instantaneously
     * if it is cached. Otherwise returns null
     * and initiates download of the page
     * to which the bitmap belongs.
     *
     * @param position of the element in the adapter.
     */
    Bitmap getBitmap(String url, int position);

    void loadArtistList();
}
