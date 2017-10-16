package com.example.onsto.musicbeta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.onsto.musicbeta.CustomView.Artists;
import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.MainActivity;
import com.example.onsto.musicbeta.R;
import com.example.onsto.musicbeta.Fragment.SongBelongArtistFragment;

import java.util.ArrayList;

/**
 * Created by onsto on 22/03/2017.
 */

public class ArtistAdapterRecyclerView extends RecyclerView.Adapter<ArtistAdapterRecyclerView.RecyclerViewHolder> {
    ArrayList<Artists> arrayArtist = new ArrayList<Artists>();
    Context context;
    FileSong fileSong = new FileSong();
    public ArtistAdapterRecyclerView(ArrayList<Artists>listData, Context context) {
        this.arrayArtist = listData;
        this.context = context;
    }
    @Override
    public ArtistAdapterRecyclerView.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.artist_lines, parent, false);
        return new RecyclerViewHolder(context,itemview);
    }

    @Override
    public void onBindViewHolder(ArtistAdapterRecyclerView.RecyclerViewHolder holder, int position) {
        Artists artists = arrayArtist.get(position);
        holder.tvArtist.setText(artists.getArtist());
//        holder.tvAlbumArtist.setText(albums.getAlbumArtist());
//        holder.imgAlbum.setImageResource(albums.getImgAlbum());
    }

    @Override
    public int getItemCount() {
        return arrayArtist.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvArtist;
        Context context;
        Intent launchNextActivity;
        SongBelongArtistFragment songBelongArtistFragment;
        FrameLayout frameLayoutSearch;

        public RecyclerViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(this);
            tvArtist = (TextView)itemView.findViewById(R.id.textViewArtist1);
            frameLayoutSearch = (FrameLayout) ((MainActivity)context).findViewById(R.id.frameLayoutSearch);
//            imgAlbum = (ImageView) itemView.findViewById(R.id.imageViewAlbum);
        }

        @Override
        public void onClick(View v) {
            frameLayoutSearch.setVisibility(View.GONE);
            songBelongArtistFragment = new SongBelongArtistFragment();
            Bundle bundle = new Bundle();
            bundle.putString("nameSongOfArtist",tvArtist.getText().toString());
            songBelongArtistFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fragmentTransaction = ( (FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, songBelongArtistFragment);
            fragmentTransaction.commit();
        }
    }
    public void setFilter(ArrayList<Artists> newArrayArtist) {
        arrayArtist = new ArrayList<Artists>();
        arrayArtist.addAll(newArrayArtist);
        notifyDataSetChanged();
    }
}
