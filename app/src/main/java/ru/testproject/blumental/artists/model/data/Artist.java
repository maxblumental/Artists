package ru.testproject.blumental.artists.model.data;

/**
 * POJO for deserialization
 * of information about one artist.
 * <p/>
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class Artist {
    private int id;

    private Cover cover;

    private String[] genres;

    private String description;

    private String link;

    private int albumNumber;

    private String name;

    private int trackNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getAlbumNumber() {
        return albumNumber;
    }

    public void setAlbumNumber(int albumNumber) {
        this.albumNumber = albumNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id
                + ", cover = " + cover
                + ", genres = " + genres
                + ", description = " + description
                + ", link = " + link
                + ", albumNumber = " + albumNumber
                + ", name = " + name
                + ", trackNumber = " + trackNumber + "]";
    }
}