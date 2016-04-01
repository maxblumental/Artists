package ru.testproject.blumental.artists.model.data;

import android.database.Cursor;

import ru.testproject.blumental.artists.model.data.db.ArtistTable;

/**
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
public class ArtistDTO {
    private String smallCoverUrl;
    private String bigCoverUrl;
    private String name;
    private String genres;
    private int albumNumber;
    private int trackNumber;
    private int artistId;
    private String biography;

    public ArtistDTO(Cursor cursor) {
        smallCoverUrl = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_SMALL_COVER_URL));
        bigCoverUrl = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_COVER_URL));
        name = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_ARTIST_NAME));
        genres = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_GENRES));
        albumNumber = cursor.getInt(cursor.getColumnIndex(ArtistTable.COLUMN_ALBUM_NUMBER));
        trackNumber = cursor.getInt(cursor.getColumnIndex(ArtistTable.COLUMN_TRACK_NUMBER));
        artistId = cursor.getInt(cursor.getColumnIndex(ArtistTable.COLUMN_ARTIST_ID));
        biography = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_BIOGRAPHY));
    }

    public int getArtistId() {
        return artistId;
    }

    public String getSmallCoverUrl() {
        return smallCoverUrl;
    }

    public String getBigCoverUrl() {
        return bigCoverUrl;
    }

    public String getName() {
        return name;
    }

    public String getGenres() {
        return genres;
    }

    public int getAlbumNumber() {
        return albumNumber;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public String getBiography() {
        return biography;
    }
}
