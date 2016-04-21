package ru.testproject.blumental.artists.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.LruCache;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;
import javax.inject.Named;

import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;

/**
 * Created by Maxim Blumental on 4/20/2016.
 * bvmaks@gmail.com
 */
public class ThumbnailDownloader extends HandlerThread {

    private static final int DOWNLOAD_MESSAGE = 0;
    private static final String TAG = "thumbnail downloader";
    ConcurrentMap<ArtistListAdapter.ViewHolder, String> requestMap = new ConcurrentHashMap<>();
    private Handler requestHandler;
    private DownloadListener listener;
    private Handler responseHandler;

    @Inject
    @Named("Small cover cache")
    LruCache<String, Bitmap> smallCoverCache;

    public ThumbnailDownloader(Handler uiHandler) {
        super(TAG);
        responseHandler = uiHandler;
        App.getComponent().inject(this);
    }

    public interface DownloadListener {
        void onThumbnailDownloaded(ArtistListAdapter.ViewHolder target, Bitmap bitmap);
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    public void queueThumbnail(ArtistListAdapter.ViewHolder target, String url) {
        if (url == null) {
            requestMap.remove(target);
        } else {
            requestMap.put(target, url);
            requestHandler.obtainMessage(DOWNLOAD_MESSAGE, target)
                    .sendToTarget();
        }
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        requestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == DOWNLOAD_MESSAGE) {
                    ArtistListAdapter.ViewHolder target = (ArtistListAdapter.ViewHolder) msg.obj;
                    handleRequest(target);
                }
            }
        };
    }

    private void handleRequest(final ArtistListAdapter.ViewHolder target) {
        final String url = requestMap.get(target);

        if (url == null) {
            return;
        }

        try {
            Bitmap bitmap = smallCoverCache.get(url);

            if (bitmap == null) {
                bitmap = Utils.downloadCover(new URL(url));
                smallCoverCache.put(url, bitmap);
            }

            final Bitmap finalBitmap = bitmap;
            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onThumbnailDownloaded(target, finalBitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearMessageQueue() {
        requestHandler.removeMessages(DOWNLOAD_MESSAGE);
        requestMap.clear();
    }
}