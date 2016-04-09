package ru.testproject.blumental.artists.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for deserialization
 * of information about one artist.
 * <p>
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class Artist implements Parcelable {
    private int id;

    private Cover cover;

    private String[] genres;

    private String description;

    private String link;

    private int albums;

    private String name;

    private int tracks;

    protected Artist(Parcel in) {
        id = in.readInt();
        cover = new Cover();
        cover.setBig(in.readString());
        genres = in.createStringArray();
        albums = in.readInt();
        tracks = in.readInt();
        description = in.readString();
        name = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

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

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id
                + ", cover = " + cover
                + ", genres = " + genres
                + ", description = " + description
                + ", link = " + link
                + ", albums = " + albums
                + ", name = " + name
                + ", tracks = " + tracks + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(cover.getBig());
        dest.writeStringArray(genres);
        dest.writeInt(albums);
        dest.writeInt(tracks);
        dest.writeString(description);
        dest.writeString(name);
    }
}