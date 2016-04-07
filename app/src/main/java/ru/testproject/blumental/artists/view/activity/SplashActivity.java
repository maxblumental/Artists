package ru.testproject.blumental.artists.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Maxim Blumental on 4/7/2016.
 * bvmaks@gmail.com
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, ArtistListActivity.class);
        startActivity(intent);
        finish();
    }
}
