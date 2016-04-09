package ru.testproject.blumental.artists.presenter;

import android.graphics.Bitmap;

import ru.testproject.blumental.artists.model.data.Artist;

/**
 * Created by Maxim Blumental on 09/04/16.
 * bvmaks@gmail.com
 */
public interface ArtistInfoPresenter extends Presenter {
    Bitmap getCoverBitmap(Artist artist);
}
