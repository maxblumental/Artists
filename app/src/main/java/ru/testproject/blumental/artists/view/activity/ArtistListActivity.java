package ru.testproject.blumental.artists.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;
import ru.testproject.blumental.artists.view.adapter.EndlessScrollListener;

public class ArtistListActivity extends AppCompatActivity implements ArtistListView {

    @Inject
    ArtistActivityPresenter presenter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.artist_list)
    RecyclerView artistList;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private ArtistListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        App.getComponent().inject(this);
        presenter.onCreate(this);

        artistList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArtistListAdapter(this, presenter);
        artistList.setAdapter(adapter);
        artistList.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int offset) {
                if (adapter.needMoreElements()) {
                    presenter.loadNextPage(offset);
                    return true;
                }
                return false;
            }
        });
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
    public void onNewPageLoaded(int newElementsCount) {
        adapter.addNewElements(newElementsCount);
    }

    @Override
    public void showArtists(List<Artist> artists) {
        adapter.setArtists(artists);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public List<Artist> getPage(int offset) {
        return adapter.getPage(offset);
    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }
}
