package ru.testproject.blumental.artists.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.other.App;
import ru.testproject.blumental.artists.presenter.ArtistInfoPresenter;

/**
 * Created by Maxim Blumental on 09/04/16.
 * bvmaks@gmail.com
 */
public class ArtistInfoActivity extends AppCompatActivity implements ArtistInfoView {

    public static final String ARTIST_KEY = "artist_key";

    @Inject
    ArtistInfoPresenter presenter;

    @Bind(R.id.cover)
    ImageView coverImageView;

    @Bind(R.id.genres)
    TextView genres;

    @Bind(R.id.numbers)
    TextView numbersTextView;

    @Bind(R.id.biography)
    TextView biographyTextView;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_info_activity);
        ButterKnife.bind(this);

        App.getComponent().inject(this);
        presenter.onCreate();
        presenter.setView(this);

        Intent intent = getIntent();
        Artist artist = intent.getParcelableExtra(ARTIST_KEY);

        String text = Arrays.toString(artist.getGenres());
        genres.setText(text.substring(1, text.length() - 1));

        String numbersText = String.format(getString(R.string.numbers_in_info),
                artist.getAlbums(), artist.getTracks());
        numbersTextView.setText(numbersText);

        biographyTextView.setText(artist.getDescription());

        setTitle(artist.getName());

        Bitmap bitmap = presenter.getCoverBitmap(artist);
        if (bitmap == null) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            coverImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void showCover(Bitmap bitmap) {
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noconnection_cover);
        }
        coverImageView.setImageBitmap(bitmap);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
