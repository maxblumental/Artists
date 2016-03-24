package ru.testproject.blumental.artists.view.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.testproject.blumental.artists.R;

/**
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class ArtistListAdapter extends RecyclerViewCursorAdapter<ArtistListAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
