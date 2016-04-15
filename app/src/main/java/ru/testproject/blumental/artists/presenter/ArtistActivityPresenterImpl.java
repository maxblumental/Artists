package ru.testproject.blumental.artists.presenter;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import ru.testproject.blumental.artists.model.Model;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.view.View;
import ru.testproject.blumental.artists.view.activity.ArtistListActivity;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public class ArtistActivityPresenterImpl extends BasePresenter implements ArtistActivityPresenter {

    public static final int PAGE_SIZE = 30;

    @Inject
    Model model;

    private ArtistListActivity view;

    private PageManager pageManager;

    @Override
    public void onCreate(View view) {
        App.getComponent().inject(this);
        this.view = (ArtistListActivity) view;
        pageManager = new PageManagerImpl();
    }

    @Override
    public void onResume() {
        //load JSON with artist list
        view.showProgress();
        loadArtistList();
    }

    @Override
    public Bitmap getBitmap(String url, int position) {
        Bitmap thumbnail = model.getThumbnail(url);

        if (thumbnail == null) {
            int i = pageManager.getPageForElement(position);

            if (pageManager.isPageLoading(i)) {
                return null;
            }

            pageManager.markPageAsLoading(i);
            List<Integer> positions = pageManager.getPositionsForPage(i);
            Subscription subscription = model.getPage(i, view.getUrls(positions))
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            view.refresh();
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showToast(e.toString());
                        }

                        @Override
                        public void onNext(Integer integer) {
                            pageManager.onPageLoaded(integer);
                        }
                    });

            addSubscription(subscription);
            return null;
        }

        return thumbnail;
    }

    @Override
    public void loadArtistList() {
        Subscription subscription = model.downloadArtistList()
                .subscribe(new Subscriber<List<Artist>>() {
                    @Override
                    public void onCompleted() {
                        view.stopProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.toString());
                    }

                    @Override
                    public void onNext(List<Artist> artists) {
                        view.showArtists(artists);
                        view.refresh();
                    }
                });

        addSubscription(subscription);
    }

    interface PageManager {
        /**
         * Determines to which page the element belongs.
         *
         * @param position of the element in the adapter.
         * @return page number.
         */
        int getPageForElement(int position);

        boolean isPageLoading(int pageNumber);

        void markPageAsLoading(int pageNumber);

        void onPageLoaded(int pageNumber);

        List<Integer> getPositionsForPage(int pageNumber);
    }

    class PageManagerImpl implements PageManager {
        private Set<Integer> loadingPages;

        public PageManagerImpl() {
            this.loadingPages = new HashSet<>();
        }

        @Override
        public int getPageForElement(int position) {
            return position / PAGE_SIZE;
        }

        @Override
        public boolean isPageLoading(int pageNumber) {
            return loadingPages.contains(pageNumber);
        }

        @Override
        public void markPageAsLoading(int pageNumber) {
            loadingPages.add(pageNumber);
        }

        @Override
        public void onPageLoaded(int pageNumber) {
            loadingPages.remove(pageNumber);
        }

        @Override
        public List<Integer> getPositionsForPage(int pageNumber) {
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < PAGE_SIZE; i++) {
                positions.add(PAGE_SIZE * pageNumber + i);
            }
            return positions;
        }
    }
}
