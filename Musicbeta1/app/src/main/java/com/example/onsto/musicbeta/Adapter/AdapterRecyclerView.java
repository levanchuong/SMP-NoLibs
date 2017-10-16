package com.example.onsto.musicbeta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.MainActivity;
import com.example.onsto.musicbeta.R;
import com.example.onsto.musicbeta.ServiceMusic;
import com.example.onsto.musicbeta.CustomView.Songs;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by onsto on 13/03/2017.
 */

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.RecyclerViewHolder> {
    ArrayList<Songs> arraySong = new ArrayList<Songs>();
    private static final String ACTION_START_MUSIC_PLAY = "com.broadcast.ACTION_MUSIC_PLAY";
//    ArrayList<Songs> arrayListSong = new ArrayList<Songs>();
//    ArrayList<File> mySongs;

    Context context;
    ArrayList<File> mySongs;
    FileSong fileSong = new FileSong();

    public AdapterRecyclerView(ArrayList<Songs> listData, Context context) {
        this.arraySong = listData;
        this.context = context;


    }

    @Override
    public AdapterRecyclerView.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.song_lines, parent, false);
        return new RecyclerViewHolder(context, itemview);
    }


    @Override
    public void onBindViewHolder(AdapterRecyclerView.RecyclerViewHolder holder, int position) {
        Songs song = arraySong.get(position);
        holder.tvSong.setText(song.getNameSong());
        holder.tvauthor.setText(song.getAuthor());
//        holder.image.setImageResource(song.getImage());
//        holder.tvItem.setText(song.getItemName());
        holder.tvPosition.setText("" + song.getPosition());
        holder.tvSongData.setText(song.getSongData());
    }

    @Override
    public int getItemCount() {
        return arraySong.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvSong, tvauthor, tvItem, tvPosition,tvSongData, tvPath;
        Context context;
        LinearLayout llSubControl;
        LinearLayout llPlayMenu;
        private Intent serviceIntent;
        FrameLayout frameLayoutSearch;

        public RecyclerViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(this);
            tvSong = (TextView) itemView.findViewById(R.id.textViewSong);
            tvSongData = (TextView) itemView.findViewById(R.id.textViewSongData);
            tvauthor = (TextView) itemView.findViewById(R.id.textViewAuthor);
            tvPosition = (TextView) itemView.findViewById(R.id.textViewPosition);
            llSubControl = (LinearLayout) ((MainActivity) context).findViewById(R.id.subControl);
            llPlayMenu = (LinearLayout) ((MainActivity) context).findViewById(R.id.player__menu);
            frameLayoutSearch = (FrameLayout) ((MainActivity)context).findViewById(R.id.frameLayoutSearch);

        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
            llSubControl.setVisibility(View.VISIBLE);
            llPlayMenu.setVisibility(View.VISIBLE);
            frameLayoutSearch.setVisibility(View.GONE);
            serviceIntent = new Intent(this.context, ServiceMusic.class);
            int positionOfSong = Integer.parseInt(tvPosition.getText().toString());
            String songData = tvSongData.getText().toString();
            broadCastIntent(positionOfSong,songData);
            serviceIntent.putExtra("position",positionOfSong);
            this.context.startService(serviceIntent);
        }
    }

    public void broadCastIntent(int positionOfSong,String songData) {
        Intent intent = new Intent(ACTION_START_MUSIC_PLAY);
        intent.putExtra("position", positionOfSong).putExtra("songData",songData);
        this.context.sendBroadcast(intent);
    }

    public void setFilter(ArrayList<Songs> newArraySongs) {
        arraySong = new ArrayList<Songs>();
        arraySong.addAll(newArraySongs);
        notifyDataSetChanged();
    }
}
