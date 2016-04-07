package ru.testproject.blumental.artists.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.ArtistDTO;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;
import ru.testproject.blumental.artists.view.adapter.EndlessScrollListener;

public class ArtistListActivity extends AppCompatActivity
        implements ArtistListView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    ArtistActivityPresenter presenter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.artist_list)
    RecyclerView artistList;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private ArtistListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        refreshLayout.setOnRefreshListener(this);

        App.getComponent().inject(this);
        presenter.onCreate(this);

        artistList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArtistListAdapter(presenter);
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
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onNewPageLoaded(int newElementsCount) {
        adapter.addNewElements(newElementsCount);
    }

    @Override
    public void showArtists(List<ArtistDTO> artists) {
        adapter.setArtistDTOs(artists);
    }

    @Override
    public List<ArtistDTO> getPageDTOs(int offset) {
        return adapter.getPageDTOs(offset);
    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

    }
}
