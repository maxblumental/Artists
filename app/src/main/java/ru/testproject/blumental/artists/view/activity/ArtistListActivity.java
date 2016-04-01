package ru.testproject.blumental.artists.view.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.ArtistProvider;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;

public class ArtistListActivity extends AppCompatActivity implements ArtistListView, SwipeRefreshLayout.OnRefreshListener {

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

        App.getComponent().inject(this);

        artistList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArtistListAdapter(presenter);
        artistList.setAdapter(adapter);
        presenter.onCreate(this);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.fetch) {
            presenter.fetchData();
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
    public void showArtists(Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void showProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    @Override
    protected void onDestroy() {
        getContentResolver().delete(ArtistProvider.CONTENT_URI, null, null);
        try {
            FileUtils.cleanDirectory(getFilesDir());
        } catch (IOException e) {
            showToast(e.toString());
        }
        super.onDestroy();
    }
}
