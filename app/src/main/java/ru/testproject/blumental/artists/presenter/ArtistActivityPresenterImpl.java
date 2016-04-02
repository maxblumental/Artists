package ru.testproject.blumental.artists.presenter;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.testproject.blumental.artists.model.Model;
import ru.testproject.blumental.artists.model.Utils;
import ru.testproject.blumental.artists.model.data.ArtistDTO;
import ru.testproject.blumental.artists.model.data.ArtistProvider;
import ru.testproject.blumental.artists.model.data.db.ArtistTable;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.view.View;
import ru.testproject.blumental.artists.view.activity.ArtistListActivity;
import ru.testproject.blumental.artists.view.adapter.ArtistListAdapter;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public class ArtistActivityPresenterImpl extends BasePresenter implements ArtistActivityPresenter {

    @Inject
    Model model;

    ArtistListActivity view;

    @Override
    public void loadFirstPage() {
        Cursor cursor = getCursorToDB();
        List<ArtistDTO> artistDTOs = new ArrayList<>();

        for (int i = 0; i < ArtistListAdapter.PAGE_SIZE && cursor.moveToNext(); i++) {
            artistDTOs.add(new ArtistDTO(cursor));
        }

        cursor.close();

        Subscription pageDownloadSubscription = model.downloadPage(view.getContext(), artistDTOs)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        view.showArtists(getCursorToDB());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.toString());
                    }

                    @Override
                    public void onNext(Integer i) {
                    }
                });

        addSubscription(pageDownloadSubscription);
    }

    @Override
    public void loadNextPage(int offset) {
        Cursor cursor = getCursorToDB();
        cursor.move(offset);
        List<ArtistDTO> artistDTOs = new ArrayList<>();

        for (int i = 0; i < ArtistListAdapter.PAGE_SIZE && cursor.moveToNext(); i++) {
            artistDTOs.add(new ArtistDTO(cursor));
        }
        cursor.close();

        Subscription pageDownloadSubscription = model.downloadPage(view.getContext(), artistDTOs)
                .subscribe(new Subscriber<Integer>() {
                    int newElementsCount = 0;

                    @Override
                    public void onCompleted() {
                        view.onNewPageLoaded(newElementsCount);
                        view.showArtists(getCursorToDB());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.toString());
                    }

                    @Override
                    public void onNext(Integer i) {
                        newElementsCount += i;
                    }
                });

        addSubscription(pageDownloadSubscription);
    }

    private Cursor getCursorToDB() {
        return view.getContext().getContentResolver()
                .query(ArtistProvider.CONTENT_URI,
                        new String[]{
                                ArtistTable.COLUMN_SMALL_COVER_URL,
                                ArtistTable.COLUMN_COVER_URL,
                                ArtistTable.COLUMN_BIOGRAPHY,
                                ArtistTable.COLUMN_ALBUM_NUMBER,
                                ArtistTable.COLUMN_TRACK_NUMBER,
                                ArtistTable.COLUMN_ARTIST_NAME,
                                ArtistTable.COLUMN_ARTIST_ID,
                                ArtistTable.COLUMN_GENRES
                        }, null, null, null);
    }

    @Override
    public Bitmap getSmallCoverBitmap(int id) {
        try {
            File smallCoverFile = Utils.getSmallCoverFile(view.getContext(), id);
            return BitmapFactory.decodeFile(smallCoverFile.getAbsolutePath());
        } catch (MalformedURLException e) {
            view.showToast(e.toString());
        }
        return null;
    }

    @Override
    public void fetchData() {
        view.showProgress();
        Subscription subscription = model.fetchData(view.getContext())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        loadFirstPage();
                        view.stopProgress();
                        view.showArtists(getCursorToDB());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.toString());
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void refresh() {
        view.showArtists(getCursorToDB());
        view.stopProgress();
    }

    @Override
    public void onCreate(View view) {
        App.getComponent().inject(this);
        this.view = (ArtistListActivity) view;
    }

    @Override
    public void onResume() {

    }
}
