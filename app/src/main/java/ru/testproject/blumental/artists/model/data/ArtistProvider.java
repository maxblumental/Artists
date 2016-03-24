package ru.testproject.blumental.artists.model.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

import ru.testproject.blumental.artists.model.data.db.ArtistDBHelper;
import ru.testproject.blumental.artists.model.data.db.ArtistTable;

/**
 * Created by Maxim Blumental on 02/02/16.
 * bvmaks@gmail.com
 */
public class ArtistProvider extends ContentProvider {

    // database
    private ArtistDBHelper database;

    // used for the UriMacher
    private static final int METAINFO = 10;
    private static final int METAINFO_ID = 20;

    private static final String AUTHORITY =
            "ru.testproject.blumental.artists.artists.contentprovider";

    private static final String BASE_PATH = "artists";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, METAINFO);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", METAINFO_ID);
    }

    @Override
    public boolean onCreate() {
        database = new ArtistDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(ArtistTable.ARTIST_TABLE);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case METAINFO:
                break;
            case METAINFO_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(ArtistTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id;
        switch (uriType) {
            case METAINFO:
                id = sqlDB.insert(ArtistTable.ARTIST_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted;
        switch (uriType) {
            case METAINFO:
                rowsDeleted = sqlDB.delete(ArtistTable.ARTIST_TABLE, selection,
                        selectionArgs);
                break;
            case METAINFO_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ArtistTable.ARTIST_TABLE,
                            ArtistTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(ArtistTable.ARTIST_TABLE,
                            ArtistTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case METAINFO:
                rowsUpdated = sqlDB.update(ArtistTable.ARTIST_TABLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case METAINFO_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ArtistTable.ARTIST_TABLE,
                            values,
                            ArtistTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(ArtistTable.ARTIST_TABLE,
                            values,
                            ArtistTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = {
                ArtistTable.COLUMN_ARTIST_NAME, ArtistTable.COLUMN_GENRES,
                ArtistTable.COLUMN_TRACK_NUMBER, ArtistTable.COLUMN_ALBUM_NUMBER,
                ArtistTable.COLUMN_ID, ArtistTable.COLUMN_COVER_URL,
                ArtistTable.COLUMN_SMALL_COVER_URL, ArtistTable.COLUMN_BIOGRAPHY
        };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
