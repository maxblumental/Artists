package ru.testproject.blumental.artists.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;
import rx.Observable;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface Model {

    void getThumbnail(ArtistListAdapter.ViewHolder holder, String url);

    void initThumbnailDownloader(ThumbnailDownloader.DownloadListener listener);

    void stopThumbnailDownloader();

    Observable<List<Artist>> downloadArtistList();

    Observable<Bitmap> downloadCover(Context context, Artist artist);
}
