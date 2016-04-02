package ru.testproject.blumental.artists.view.adapter;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.ArtistDTO;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class ArtistListAdapter extends RecyclerViewCursorAdapter<ArtistListAdapter.ViewHolder> {

    private ArtistActivityPresenter presenter;
    public final static int PAGE_SIZE = 15;
    private int count = PAGE_SIZE;

    private static final int NORMAL_TYPE = 0;
    private static final int FOOTER_TYPE = 1;

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()
                && getItemCount() < super.getItemCount()) {
            return FOOTER_TYPE;
        }
        return NORMAL_TYPE;
    }

    public ArtistListAdapter(ArtistActivityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getItemCount() {
        return Math.min(count, super.getItemCount());
    }

    public boolean needMoreElements() {
        return count < super.getItemCount();
    }

    public void addNewElements(int newElementsCount) {
        count = Math.min(count + newElementsCount, super.getItemCount());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == FOOTER_TYPE) {
            View footer = inflater
                    .inflate(R.layout.loading_new_item, parent, false);
            return new ViewHolder(footer, FOOTER_TYPE);
        }

        View view = inflater
                .inflate(R.layout.artist_list_item, parent, false);
        return new ViewHolder(view, NORMAL_TYPE);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == FOOTER_TYPE) {
            return;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {

        ArtistDTO artistDTO = new ArtistDTO(cursor);

        holder.artistName.setText(artistDTO.getName());
        holder.genres.setText(artistDTO.getGenres());

        String songAlbumNums = String.format("%d albums, %d tracks", artistDTO.getAlbumNumber(),
                artistDTO.getTrackNumber());
        holder.albumSongNums.setText(songAlbumNums);

        Bitmap bitmap = presenter.getSmallCoverBitmap(artistDTO.getArtistId());
        holder.smallCover.setImageBitmap(bitmap);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.smallCover)
        ImageView smallCover;

        @Bind(R.id.artistName)
        TextView artistName;

        @Bind(R.id.genres)
        TextView genres;

        @Bind(R.id.albumSongNums)
        TextView albumSongNums;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == NORMAL_TYPE) {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
