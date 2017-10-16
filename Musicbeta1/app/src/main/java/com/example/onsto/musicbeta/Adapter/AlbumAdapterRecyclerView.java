package com.example.onsto.musicbeta.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onsto.musicbeta.CustomView.Albums;
import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.MainActivity;
import com.example.onsto.musicbeta.R;
import com.example.onsto.musicbeta.Fragment.SongBelongAlbumFragment;

import java.util.ArrayList;

/**
 * Created by onsto on 22/03/2017.
 */

public class AlbumAdapterRecyclerView extends RecyclerView.Adapter<AlbumAdapterRecyclerView.RecyclerViewHolder> {
    ArrayList<Albums> arrayAlbum = new ArrayList<Albums>();
    Context context;
    FileSong fileSong = new FileSong();
    Activity activity = (Activity) context;



    public AlbumAdapterRecyclerView(ArrayList<Albums>listData, Context context) {
        this.arrayAlbum = listData;
        this.context = context;
    }
    @Override
    public AlbumAdapterRecyclerView.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.album_lines, parent, false);
        return new RecyclerViewHolder(context,itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Albums albums = arrayAlbum.get(position);
        holder.tvAlbumName.setText(albums.getAlbumName());
        holder.tvAlbumArtist.setText(albums.getAlbumArtist());
        holder.imgAlbum.setImageDrawable(albums.getDrAlbum());

    }

    @Override
    public int getItemCount() {
        return arrayAlbum.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvAlbumName,tvAlbumArtist;
        ImageView imgAlbum;
        Context context;
        SongBelongAlbumFragment songBelongAlbumFragment;
        FragmentManager fragmentManager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
//        SlidingUpPanelLayout slidingUpPanelLayout;
        FrameLayout frameLayoutSearch;

        public RecyclerViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(this);
            tvAlbumName = (TextView)itemView.findViewById(R.id.textViewAlbumName);
            tvAlbumArtist = (TextView)itemView.findViewById(R.id.textViewAlbumArtist);
//            tvItem = (TextView)itemView.findViewById(R.id.textViewItem);
            imgAlbum = (ImageView) itemView.findViewById(R.id.imageViewAlbum);
            frameLayoutSearch = (FrameLayout) ((MainActivity)context).findViewById(R.id.frameLayoutSearch);


//            slidingUpPanelLayout = (SlidingUpPanelLayout) itemView.findViewById(R.id.slidingLayout);

        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//            String name = tvAlbumArtist.getText().toString();

//            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            frameLayoutSearch.setVisibility(View.GONE);
            songBelongAlbumFragment = new SongBelongAlbumFragment();
            Bundle bundle = new Bundle();
            bundle.putString("nameSongOfAlbum",tvAlbumName.getText().toString());
            songBelongAlbumFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fragmentTransaction = ( (FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, songBelongAlbumFragment);
            fragmentTransaction.commit();
        }


    }
    public void setFilter(ArrayList<Albums> newArrayAlbum) {
        arrayAlbum = new ArrayList<Albums>();
        arrayAlbum.addAll(newArrayAlbum);
        notifyDataSetChanged();
    }
}
