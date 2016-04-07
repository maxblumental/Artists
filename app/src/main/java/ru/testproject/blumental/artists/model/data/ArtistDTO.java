package ru.testproject.blumental.artists.model.data;

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
