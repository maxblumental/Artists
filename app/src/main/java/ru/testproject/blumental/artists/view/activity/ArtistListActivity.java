package ru.testproject.blumental.artists.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;
import ru.testproject.blumental.artists.view.fragment.RetainedFragment;

public class ArtistListActivity extends AppCompatActivity implements ArtistListView {

    private final static String RETAINED_FRAGMENT_TAG = "retained fragment tag";

    @Inject
    ArtistActivityPresenter presenter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.artist_list)
    RecyclerView artistList;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.no_internet_screen)
    RelativeLayout noInternetScreen;

    @OnClick(R.id.try_again_button)
    public void tryAgain() {
        presenter.loadArtistList();
    }

    private ArtistListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        List<Artist> artists = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        RetainedFragment fragment
                = (RetainedFragment) fragmentManager.findFragmentByTag(RETAINED_FRAGMENT_TAG);
        if (fragment == null) {
            App.getComponent().inject(this);
            fragment = new RetainedFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, RETAINED_FRAGMENT_TAG)
                    .commit();
            presenter.onCreate();
        } else {
            presenter = (ArtistActivityPresenter) fragment.getPresenter();
            artists = fragment.getArtists();
        }
        presenter.setView(this);

        artistList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArtistListAdapter(this, presenter);
        if (artists != null) {
            showArtists(artists);
        }
        artistList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void stopProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showArtists(List<Artist> artists) {
        adapter.setArtists(artists);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        noInternetScreen.setVisibility(View.GONE);
        artistList.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean needElements() {
        return adapter.getItemCount() == 0;
    }

    @Override
    public void showNoInternetScreen() {
        progressBar.setVisibility(View.GONE);
        artistList.setVisibility(View.GONE);
        noInternetScreen.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        FragmentManager fragmentManager = getSupportFragmentManager();
        RetainedFragment fragment
                = (RetainedFragment) fragmentManager.findFragmentByTag(RETAINED_FRAGMENT_TAG);
        if (fragment != null) {
            fragment.setArtists(adapter.getArtists());
            fragment.setPresenter(presenter);
        }
        super.onDestroy();
    }
}
