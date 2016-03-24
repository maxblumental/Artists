package ru.testproject.blumental.artists.model.data.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Maxim Blumental on 02/02/16.
 * bvmaks@gmail.com
 */
public class ArtistTable {
    public static final String ARTIST_TABLE = "artist_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "artist_name";
    public static final String COLUMN_GENRES = "genres";
    public static final String COLUMN_TRACK_NUMBER = "song_number";
    public static final String COLUMN_ALBUM_NUMBER = "album_number";
    public static final String COLUMN_COVER_URL = "cover_url";
    public static final String COLUMN_SMALL_COVER_URL = "small_cover_url";
    public static final String COLUMN_BIOGRAPHY = "small_cover_url";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + ARTIST_TABLE + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ARTIST_NAME + " text not null , "
            + COLUMN_GENRES + " text not null, "
            + COLUMN_TRACK_NUMBER + " integer, "
            + COLUMN_ALBUM_NUMBER + " integer, "
            + COLUMN_COVER_URL + " text not null"
            + COLUMN_SMALL_COVER_URL + " text not null"
            + COLUMN_BIOGRAPHY + " text not null,"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(ArtistTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + ARTIST_TABLE);
        onCreate(database);
    }
}
