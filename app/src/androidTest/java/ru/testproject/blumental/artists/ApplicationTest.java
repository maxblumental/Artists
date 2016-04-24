package ru.testproject.blumental.artists;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ProgressBar;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import ru.testproject.blumental.artists.view.activity.ArtistInfoActivity;
import ru.testproject.blumental.artists.view.activity.ArtistListActivity;

/**
 * Tests for the application.
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<ArtistListActivity> {
    public ApplicationTest() {
        super(ArtistListActivity.class);
    }

    private Solo solo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * Click an element in the list of artists
     * and wait until the big cover is loaded.
     * Assert that the detailed screen is opened.
     */
    public void testOpenArtistInfoScreen() {
        solo.waitForActivity(ArtistListActivity.class);
        solo.clickInRecyclerView(0);
        assertTrue(solo.waitForActivity(ArtistInfoActivity.class));

        //wait until the progress bar
        //becomes invisible, hence the loading is finished
        final ProgressBar progressBar = (ProgressBar) solo.getView(R.id.progressBar);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return progressBar.getVisibility() == View.GONE;
            }
        }, 60_000);
    }

    /**
     * Make sure that the artist list
     * is preserved during device rotation.
     */
    public void testPreservePositionAfterRotation() {
        RecyclerView view = (RecyclerView) solo.getView(R.id.artist_list);
        LinearLayoutManager layoutManager = (LinearLayoutManager) view.getLayoutManager();

        final ProgressBar progressBar = (ProgressBar) solo.getView(R.id.progressBar);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return progressBar.getVisibility() == View.GONE;
            }
        }, 60_000);

        solo.scrollRecyclerViewToBottom(0);

        solo.setActivityOrientation(Solo.PORTRAIT);

        int before = layoutManager.findFirstVisibleItemPosition();

        solo.setActivityOrientation(Solo.LANDSCAPE);

        int after = layoutManager.findFirstVisibleItemPosition();

        solo.setActivityOrientation(Solo.PORTRAIT);

        assertEquals(before, after);
    }
}