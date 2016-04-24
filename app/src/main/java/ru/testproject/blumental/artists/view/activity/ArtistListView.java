package ru.testproject.blumental.artists.view.activity;

import java.util.List;

import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.view.View;

/**
 * Specific methods of ArtistListActivity
 * as a View in the MVP pattern.
 * <p/>
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface ArtistListView extends View {
    void stopProgress();

    void showArtists(List<Artist> artists);

    void showProgress();

    /**
     * Whether to initiate JSON loading or not.
     */
    boolean needElements();

    void showNoInternetScreen();
}
