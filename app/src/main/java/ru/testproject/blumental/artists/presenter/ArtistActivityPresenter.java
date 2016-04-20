package ru.testproject.blumental.artists.presenter;

import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public interface ArtistActivityPresenter extends Presenter {

    void getBitmap(ArtistListAdapter.ViewHolder holder, String url);

    void loadArtistList();
}
