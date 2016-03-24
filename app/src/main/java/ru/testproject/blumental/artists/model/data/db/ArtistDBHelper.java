package ru.testproject.blumental.artists.model.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maxim Blumental on 02/02/16.
 * bvmaks@gmail.com
 */
public class ArtistDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "artists.db";
    private static final int DATABASE_VERSION = 1;

    public ArtistDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArtistTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArtistTable.onUpgrade(db, oldVersion, newVersion);
    }
}
