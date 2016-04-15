package ru.testproject.blumental.artists.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.net.URL;
import java.util.List;

import ru.testproject.blumental.artists.model.data.Artist;
import rx.Observable;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface Model {
    /**
     * Get bitmap from the url.
     * Returns the bitmap if it is cached.
     * Otherwise returns null.
     */
    Bitmap getThumbnail(String url);

    /**
     * Download images from the specified URLs.
     *
     * @return Observable with page number.
     */
    Observable<Integer> getPage(int pageNumber, List<URL> urls);

    Observable<List<Artist>> downloadArtistList();

    Observable<Bitmap> downloadCover(Context context, Artist artist);
}
