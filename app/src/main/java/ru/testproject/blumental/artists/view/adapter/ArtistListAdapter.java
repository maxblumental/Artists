package ru.testproject.blumental.artists.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.testproject.blumental.artists.R;
import ru.testproject.blumental.artists.model.data.Artist;
import ru.testproject.blumental.artists.presenter.ArtistActivityPresenter;
import ru.testproject.blumental.artists.view.activity.ArtistInfoActivity;

/**
 * Adapter providing elements
 * for the list of artists.
 * <p/>
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {

    private WeakReference<Context> context;
    private List<Artist> artists;
    private WeakReference<ArtistActivityPresenter> presenter;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public ArtistListAdapter(Context context, ArtistActivityPresenter presenter) {
        this.context = new WeakReference<>(context);
        this.presenter = new WeakReference<>(presenter);
    }

    @Override
    public int getItemCount() {
        return artists == null ? 0 : artists.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater
                .inflate(R.layout.artist_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Artist artist = artists.get(position);

        holder.artistName.setText(artist.getName());

        String[] genres = artist.getGenres();
        String text = Arrays.toString(genres);
        holder.genres.setText(text.substring(1, text.length() - 1));

        String albumSongNumbers = String.format(context.get().getString(R.string.numbers_in_list),
                artist.getAlbums(), artist.getTracks());

        holder.albumSongNumbers.setText(albumSongNumbers);

        if (presenter != null) {
            presenter.get().getBitmap(holder, artist.getCover().getSmall());
        }
        holder.thumbnail.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.VISIBLE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.thumbnail)
        ImageView thumbnail;

        @Bind(R.id.artistName)
        TextView artistName;

        @Bind(R.id.genres)
        TextView genres;

        @Bind(R.id.albumSongNums)
        TextView albumSongNumbers;

        @Bind(R.id.progressBar)
        ProgressBar progressBar;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // Switch to detailed info about an artist
            // when according list item is clicked.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.get(), ArtistInfoActivity.class);

                    int id = ViewHolder.this.getAdapterPosition();
                    Artist value = artists.get(id);
                    intent.putExtra(ArtistInfoActivity.ARTIST_KEY, value);

                    context.get().startActivity(intent);
                }
            });
        }

        public void setThumbnail(Bitmap bitmap) {
            progressBar.setVisibility(View.GONE);
            thumbnail.setVisibility(View.VISIBLE);
            if (bitmap == null) {
                bitmap = BitmapFactory
                        .decodeResource(context.get().getResources(), R.drawable.noconnection_cover);
            }
            thumbnail.setImageBitmap(bitmap);
        }
    }
}
