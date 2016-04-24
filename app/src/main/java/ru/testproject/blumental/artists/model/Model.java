package ru.testproject.blumental.artists.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;
import rx.Observable;

/**
 * The interface of Model in the MVP pattern.
 * <p/>
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface Model {

    /**
     * Get thumbnail from the given url and set it via the holder.
     *
     * @param holder ViewHolder from Artist list's adapter
     *               for setting the image when its loaded.
     * @param url    where to get the image.
     */
    void getThumbnail(ArtistListAdapter.ViewHolder holder, String url);

    /**
     * Prepare thumbnail downloader.
     */
    void initThumbnailDownloader(Context context, ThumbnailDownloader.DownloadListener listener);

    /**
     * Remove all queued requests
     * in the thumbnail downloader.
     */
    void stopThumbnailDownloader(boolean isFinishing);

    /**
     * Download the JSON with artists.
     */
    Observable<List<Artist>> downloadArtistList();

    /**
     * Get big cover for the specified artist object.
     */
    Observable<Bitmap> downloadCover(Context context, Artist artist);

    void initThumbnailDownloaderContext(Context context);
}
