package ru.testproject.blumental.artists.view.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.ArtistDTO;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {

    private List<ArtistDTO> artistDTOs;
    private ArtistActivityPresenter presenter;

    public final static int PAGE_SIZE = 15;
    private int count = PAGE_SIZE;

    private static final int NORMAL_TYPE = 0;
    private static final int FOOTER_TYPE = 1;

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()
                && getItemCount() < artistDTOs.size()) {
            return FOOTER_TYPE;
        }
        return NORMAL_TYPE;
    }

    public void setArtistDTOs(List<ArtistDTO> artistDTOs) {
        this.artistDTOs = artistDTOs;
    }

    public ArtistListAdapter(ArtistActivityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getItemCount() {
        return Math.min(count, artistDTOs.size());
    }

    public boolean needMoreElements() {
        return count < artistDTOs.size();
    }

    public void addNewElements(int newElementsCount) {
        count = Math.min(count + newElementsCount, artistDTOs.size());
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
        ArtistDTO artistDTO = artistDTOs.get(position);

        holder.artistName.setText(artistDTO.getName());
        holder.genres.setText(artistDTO.getGenres());

        String albumSongNumbers = String.format("%d albums, %d tracks",
                artistDTO.getAlbumNumber(), artistDTO.getTrackNumber());

        holder.albumSongNumbers.setText(albumSongNumbers);

        Bitmap bitmap = presenter.getThumbnailBitmap(artistDTO.getArtistId());
        holder.thumbnail.setImageBitmap(bitmap);
    }

    public List<ArtistDTO> getPageDTOs(int offset) {
        return new ArrayList<>(artistDTOs.subList(offset, offset + PAGE_SIZE));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.thumbnail)
        ImageView thumbnail;

        @Bind(R.id.artistName)
        TextView artistName;

        @Bind(R.id.genres)
        TextView genres;

        @Bind(R.id.albumSongNums)
        TextView albumSongNumbers;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == NORMAL_TYPE) {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
