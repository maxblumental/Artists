package ru.testproject.blumental.artists.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import ru.testproject.blumental.artists.model.data.Artist;
import rx.Observable;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface Model {
    Observable<Integer> downloadPage(Context context, List<Artist> artists);
    Observable<List<Artist>> downloadArtistList();
    Observable<Bitmap> downloadCover(Context context, Artist artist);
}
