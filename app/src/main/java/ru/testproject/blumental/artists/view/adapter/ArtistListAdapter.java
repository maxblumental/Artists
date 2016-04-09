package ru.testproject.blumental.artists.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;
import ru.testproject.blumental.artists.view.activity.ArtistInfoActivity;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {

    private Context context;
    private List<Artist> artists;
    private ArtistActivityPresenter presenter;

    public final static int PAGE_SIZE = 30;
    private int count = PAGE_SIZE;

    private static final int NORMAL_TYPE = 0;
    private static final int FOOTER_TYPE = 1;

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()
                && getItemCount() < artists.size()) {
            return FOOTER_TYPE;
        }
        return NORMAL_TYPE;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public ArtistListAdapter(Context context, ArtistActivityPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getItemCount() {
        int realSize = artists == null ? 0 : artists.size();
        return Math.min(count, realSize);
    }

    public boolean needMoreElements() {
        int realSize = artists == null ? 0 : artists.size();
        return count < realSize;
    }

    public void addNewElements(int newElementsCount) {
        count = Math.min(count + newElementsCount, artists.size());
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

        Artist artist = artists.get(position);

        holder.artistName.setText(artist.getName());

        String[] genres = artist.getGenres();
        String text = Arrays.toString(genres);
        holder.genres.setText(text.substring(1, text.length() - 1));

        String albumSongNumbers = String.format(context.getString(R.string.numbers_in_list),
                artist.getAlbums(), artist.getTracks());

        holder.albumSongNumbers.setText(albumSongNumbers);

        Bitmap bitmap = presenter.getThumbnailBitmap(artist.getId());
        holder.thumbnail.setImageBitmap(bitmap);
    }

    public List<Artist> getPage(int offset) {
        int end = Math.min(offset + PAGE_SIZE, artists.size());
        return new ArrayList<>(artists.subList(offset, end));
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

        public ViewHolder(final View itemView, int viewType) {
            super(itemView);
            if (viewType == NORMAL_TYPE) {
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ArtistInfoActivity.class);

                        int id = ViewHolder.this.getAdapterPosition();
                        Artist value = artists.get(id);
                        intent.putExtra(ArtistInfoActivity.ARTIST_KEY, value);

                        context.startActivity(intent);
                    }
                });
            }

        }
    }
}
