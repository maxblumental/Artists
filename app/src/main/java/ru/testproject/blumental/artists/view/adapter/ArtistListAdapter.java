package ru.testproject.blumental.artists.view.adapter;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.db.ArtistTable;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class ArtistListAdapter extends RecyclerViewCursorAdapter<ArtistListAdapter.ViewHolder> {

    private ArtistActivityPresenter presenter;

    public ArtistListAdapter(ArtistActivityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        if (!presenter.isSmallCoverLoaded(cursor)) {
            holder.progressBar.setVisibility(View.VISIBLE);
            presenter.loadNextPage(cursor);
            return;
        }

        holder.progressBar.setVisibility(View.GONE);

        String name = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_ARTIST_NAME));
        String genres = cursor.getString(cursor.getColumnIndex(ArtistTable.COLUMN_GENRES));
        int trackNumber = cursor.getInt(cursor.getColumnIndex(ArtistTable.COLUMN_TRACK_NUMBER));
        int albumNumber = cursor.getInt(cursor.getColumnIndex(ArtistTable.COLUMN_ALBUM_NUMBER));

        holder.artistName.setText(name);
        holder.genres.setText(genres);

        String songAlbumNums = String.format("%d albums, %d tracks", albumNumber, trackNumber);
        holder.albumSongNums.setText(songAlbumNums);

        Bitmap bitmap = presenter.getSmallCoverBitmap(name);
        holder.smallCover.setImageBitmap(bitmap);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.progressBar)
        ProgressBar progressBar;

        @Bind(R.id.smallCover)
        ImageView smallCover;

        @Bind(R.id.artistName)
        TextView artistName;

        @Bind(R.id.genres)
        TextView genres;

        @Bind(R.id.albumSongNums)
        TextView albumSongNums;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
