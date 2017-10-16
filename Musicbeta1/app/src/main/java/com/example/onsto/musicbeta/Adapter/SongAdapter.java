package com.example.onsto.musicbeta.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.onsto.musicbeta.CustomView.Songs;
import com.example.onsto.musicbeta.CustomView.ViewHolder;
import com.example.onsto.musicbeta.R;

import java.util.ArrayList;

/**
 * Created by onsto on 21/02/2017.
 */

public class SongAdapter extends BaseAdapter {

    public Context myContext;
//    public int myLayout;
    public ArrayList<Songs> arraySong;
    public static LayoutInflater inflater = null;

    public SongAdapter(Context context, ArrayList<Songs> SongList){
        myContext = context;

        arraySong = SongList;
    }

    @Override
    public int getCount() {
        return arraySong.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View layout = convertView;
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (layout == null){
            viewHolder = new ViewHolder();

            layout = inflater.inflate(R.layout.song_lines, null);
            viewHolder.tvSong = (TextView)layout.findViewById(R.id.textViewSong);
            viewHolder.tvauthor = (TextView)layout.findViewById(R.id.textViewAuthor);
            layout.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)layout.getTag();
        }

        Songs song = arraySong.get(position);
        viewHolder.tvSong.setText(song.getNameSong());
        viewHolder.tvauthor.setText(song.getAuthor());
//        viewHolder.image.setImageResource(song.getImage());
        return layout;
    }
}
