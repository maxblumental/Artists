package ru.testproject.blumental.artists.view.activity;

import java.util.List;

import ru.testproject.blumental.artists.model.data.ArtistDTO;
import ru.testproject.blumental.artists.view.View;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface ArtistListView extends View {
    void stopProgress();

    void onNewPageLoaded(int newElementsCount);

    void showArtists(List<ArtistDTO> artists);

    List<ArtistDTO> getPageDTOs(int offset);

    void refresh();
}
