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
import ru.testproject.blumental.artists.model.data.ArtistDTO;
import ru.testproject.blumental.artists.model.data.db.ArtistTable;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class ArtistListAdapter extends RecyclerViewCursorAdapter<ArtistListAdapter.ViewHolder> {

    private ArtistActivityPresenter presenter;
    private boolean isLoadingPage = false;

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
        int id = cursor.getInt(cursor.getColumnIndex(ArtistTable.COLUMN_ARTIST_ID));

        if (!presenter.isSmallCoverLoaded(id)) {
            if (isLoadingPage) {
                return;
            }
            holder.progressBar.setVisibility(View.VISIBLE);
            presenter.loadNextPage(cursor);
            isLoadingPage = true;
            return;
        }

        isLoadingPage = false;
        holder.progressBar.setVisibility(View.GONE);

        ArtistDTO artistDTO = new ArtistDTO(cursor);

        holder.artistName.setText(artistDTO.getName());
        holder.genres.setText(artistDTO.getGenres());

        String songAlbumNums = String.format("%d albums, %d tracks", artistDTO.getAlbumNumber(),
                artistDTO.getTrackNumber());
        holder.albumSongNums.setText(songAlbumNums);

        Bitmap bitmap = presenter.getSmallCoverBitmap(id);
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
