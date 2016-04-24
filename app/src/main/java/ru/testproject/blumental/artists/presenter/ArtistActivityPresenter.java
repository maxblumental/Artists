package ru.testproject.blumental.artists.presenter;

import android.content.Context;

import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;

/**
 * Presenter for ArtistListActivity.
 * <p/>
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public interface ArtistActivityPresenter extends Presenter {

    void getBitmap(ArtistListAdapter.ViewHolder holder, String url);

    void loadArtistList();

    void initDownloaderContext(Context context);
}
