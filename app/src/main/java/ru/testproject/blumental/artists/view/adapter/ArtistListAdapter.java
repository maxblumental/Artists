package ru.testproject.blumental.artists.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
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

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public ArtistListAdapter(Context context, ArtistActivityPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getItemCount() {
        int size = artists == null ? 0 : artists.size();
        return size;
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

        String albumSongNumbers = String.format(context.getString(R.string.numbers_in_list),
                artist.getAlbums(), artist.getTracks());

        holder.albumSongNumbers.setText(albumSongNumbers);

        Bitmap bitmap = presenter.getBitmap(artist.getCover().getSmall(), position);
        if (bitmap == null) {
            holder.thumbnail.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.thumbnail.setVisibility(View.VISIBLE);
            holder.thumbnail.setImageBitmap(bitmap);
        }
    }

    public List<URL> getUrls(List<Integer> positions) throws MalformedURLException {
        List<URL> urls = new ArrayList<>();
        for (Integer position : positions) {
            if (position >= artists.size()) {
                continue;
            }
            String urlString = artists.get(position).getCover().getSmall();
            urls.add(new URL(urlString));
        }
        return urls;
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

        @Bind(R.id.progressBar)
        ProgressBar progressBar;

        public ViewHolder(final View itemView) {
            super(itemView);
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
