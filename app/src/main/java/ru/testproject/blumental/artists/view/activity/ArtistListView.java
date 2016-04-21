package ru.testproject.blumental.artists.view.activity;

import java.net.URL;
import java.util.List;

import ru.testproject.blumental.artists.model.ThumbnailDownloader;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.view.View;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface ArtistListView extends View {
    void stopProgress();

    void showArtists(List<Artist> artists);

    void showProgress();

    void refresh();

    boolean needElements();
}
