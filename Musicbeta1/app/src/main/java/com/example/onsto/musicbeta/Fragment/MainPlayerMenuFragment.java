package com.example.onsto.musicbeta.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlayerMenuFragment extends Fragment {


    public MainPlayerMenuFragment() {
        // Required empty public constructor
    }

    private FileSong fileSong = new FileSong();
    private ImageView imgLoadSong;
    private Animation animRotate;
    private BroadcastReceiver broadcastReceiver;
    private int position;
    private static final String ACTION_START_MUSIC_PLAY = "com.broadcast.ACTION_MUSIC_PLAY";
    private static final String ACTION_MUSIC_PAUSE = "com.broadcast.service.music.ACTION_MUSIC_PAUSE";
    private static final String ACTION_MUSIC_PLAY = "com.broadcast.service.music.ACTION_MUSIC_PLAY";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_player_menu, container, false);
        imgLoadSong = (ImageView) view.findViewById(R.id.imageViewLoadSong);
        animRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rotate);
        fileSong.ScanAllAlbum(getActivity());
        fileSong.ScanAllMusic(getActivity());
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case ACTION_START_MUSIC_PLAY:
                        position = intent.getIntExtra("position", 0);
                        ImgLoadAnimation(position);
                        break;
                    case ACTION_MUSIC_PLAY:
                        position = intent.getIntExtra("position", 0);
                        ImgLoadAnimation(position);
                        break;
                    case ACTION_MUSIC_PAUSE:
                        position = intent.getIntExtra("position", 0);
                        ImgLoadAnimation(position);
                        break;

                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_MUSIC_PLAY));
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_START_MUSIC_PLAY));
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_MUSIC_PAUSE));

        return view;
    }
    public void ImgLoadAnimation(int position) {
        //load animation
        for (int i = 0; i < FileSong.arrayListAlbum.size(); i++) {
            if (FileSong.arrayListSong.get(position).getAuthor().equals(FileSong.arrayListAlbum.get(i).getAlbumArtist())) {
                imgLoadSong.setImageDrawable(FileSong.arrayListAlbum.get(i).getDrAlbum());
                return;
            }
        }
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_player_black);
        imgLoadSong.setImageBitmap(myBitmap);
        imgLoadSong.startAnimation(animRotate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
