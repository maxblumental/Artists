package ru.testproject.blumental.artists.presenter;

import android.database.Cursor;
import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.testproject.blumental.artists.model.Model;
import ru.testproject.blumental.artists.model.data.ArtistProvider;
import ru.testproject.blumental.artists.model.data.db.ArtistTable;
import ru.testproject.blumental.artists.view.View;
import ru.testproject.blumental.artists.view.activity.ArtistListActivity;
import rx.Subscriber;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public class ArtistActivityPresenterImpl implements ArtistActivityPresenter {

    @Inject
    Model model;

    ArtistListActivity view;

    @Override
    public boolean isSmallCoverLoaded(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_ARTIST_NAME));
        File file = new File(view.getContext().getFilesDir(), name);
        return file.exists();
    }

    @Override
    public void loadNextPage(Cursor cursor) {
        List<String> urls = new ArrayList<>();
        String url = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_SMALL_COVER_URL));
        urls.add(url);

        int i = 1;
        while (i < 20 && cursor.moveToNext()) {
            url = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_SMALL_COVER_URL));
            urls.add(url);
            i++;
        }

        model.downloadPage(urls).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Cursor cursor = view.getContext().getContentResolver()
                        .query(ArtistProvider.CONTENT_URI,
                                new String[]{
                                        ArtistTable.COLUMN_SMALL_COVER_URL,
                                        ArtistTable.COLUMN_ALBUM_NUMBER,
                                        ArtistTable.COLUMN_TRACK_NUMBER,
                                        ArtistTable.COLUMN_ARTIST_NAME,
                                        ArtistTable.COLUMN_GENRES
                                }, null, null, null);
                view.showArtists(cursor);
            }

            @Override
            public void onError(Throwable e) {
                view.showToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    @Override
    public Bitmap getSmallCoverBitmap(String name) {
        return null;
    }

    @Override
    public void onCreate(View view) {
        this.view = (ArtistListActivity) view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
