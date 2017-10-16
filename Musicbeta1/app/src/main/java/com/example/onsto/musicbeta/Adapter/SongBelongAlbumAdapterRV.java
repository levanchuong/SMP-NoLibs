package com.example.onsto.musicbeta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.onsto.musicbeta.MainActivity;
import com.example.onsto.musicbeta.R;
import com.example.onsto.musicbeta.ServiceMusic;
import com.example.onsto.musicbeta.CustomView.Songs;

import java.util.ArrayList;

/**
 * Created by onsto on 26/03/2017.
 */

public class SongBelongAlbumAdapterRV extends RecyclerView.Adapter<SongBelongAlbumAdapterRV.RecyclerViewHolder>  {
    private ArrayList<Songs> arraySong = new ArrayList<Songs>();
    private Context context;
    private static final String ACTION_START_MUSIC_PLAY = "com.broadcast.ACTION_MUSIC_PLAY";
    public SongBelongAlbumAdapterRV(ArrayList<Songs>listData, Context context) {
        this.arraySong = listData;
        this.context = context;


    }
    @Override
    public SongBelongAlbumAdapterRV.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.song_lines, parent, false);
        return new RecyclerViewHolder(context,itemview);
    }



    @Override
    public void onBindViewHolder(SongBelongAlbumAdapterRV.RecyclerViewHolder holder, int position) {
        Songs song = arraySong.get(position);
        holder.tvSong.setText(song.getNameSong());
        holder.tvauthor.setText(song.getAuthor());
//        holder.image.setImageResource(song.getImage());
//        holder.tvItem.setText(song.getItemName());
        holder.tvPosition.setText(""+song.getPosition());
    }

    @Override
    public int getItemCount() {
        return arraySong.size();
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvSong,tvauthor,tvItem,tvPosition;
        ImageView image;
        Context context;
        Intent launchNextActivity;
        LinearLayout llSubControl;
        LinearLayout llPlayMenu;
        private Intent serviceIntent;

        public RecyclerViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(this);
            tvSong = (TextView)itemView.findViewById(R.id.textViewSong);
            tvauthor = (TextView)itemView.findViewById(R.id.textViewAuthor);
//            tvItem = (TextView)itemView.findViewById(R.id.textViewItem);
            tvPosition = (TextView)itemView.findViewById(R.id.textViewPosition);
            llSubControl = (LinearLayout) ((MainActivity) context).findViewById(R.id.subControl);
            llPlayMenu = (LinearLayout) ((MainActivity) context).findViewById(R.id.player__menu);
        }

        @Override
        public void onClick(View v) {
            llSubControl.setVisibility(View.VISIBLE);
            llPlayMenu.setVisibility(View.VISIBLE);
            serviceIntent = new Intent(this.context, ServiceMusic.class);
            int positionOfSong = Integer.parseInt(tvPosition.getText().toString());
            broadCastIntent(positionOfSong);
            serviceIntent.putExtra("position",positionOfSong);
            this.context.startService(serviceIntent);
        }
    }
    public void broadCastIntent(int positionOfSong) {
        Intent intent = new Intent(ACTION_START_MUSIC_PLAY);
        intent.putExtra("position", positionOfSong);
        this.context.sendBroadcast(intent);
    }
}

