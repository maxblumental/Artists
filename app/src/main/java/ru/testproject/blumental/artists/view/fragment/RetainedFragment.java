package ru.testproject.blumental.artists.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.presenter.Presenter;

/**
 * Fragment for storing objects that
 * need to survive configuration changes:
 * presenter and loaded artist list.
 * <p/>
 * Created by Maxim Blumental on 4/21/2016.
 * bvmaks@gmail.com
 */
public class RetainedFragment extends Fragment {

    private Presenter presenter;

    private List<Artist> artists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
