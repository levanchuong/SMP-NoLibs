package com.example.onsto.musicbeta.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onsto.musicbeta.LoadSdCard.LoadLyricSDcard;
import com.example.onsto.musicbeta.R;

import java.io.File;
import java.util.ArrayList;

//import cn.zhaiyifan.lyric.LyricUtils;
//import cn.zhaiyifan.lyric.widget.LyricView;
import me.zhengken.lyricview.LyricView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LyricsFragment extends Fragment implements LyricView.OnPlayerClickListener {


    private static final String TAG = LyricsFragment.class.getSimpleName();

    public LyricsFragment() {
        // Required empty public constructor
    }

    private LyricView mLyricView;
    private Runnable mrunnable ;
    private Handler mhandler = new Handler();
//    private String STR_PATH = Environment.getExternalStorageDirectory()+""; // path sd card cho android 4.4.x tro xuong
    private String STR_PATH = System.getenv("SECONDARY_STORAGE"); // path sd card cho android 4.4.x tro len

    private LoadLyricSDcard loadLyric = new LoadLyricSDcard();
    private ArrayList<File> fileArrayList = new ArrayList<File>();
    private static final String ACTION_START_MUSIC_PLAY = "com.broadcast.ACTION_MUSIC_PLAY";
    private static final String ACTION_MUSIC_PLAY = "com.broadcast.service.music.ACTION_MUSIC_PLAY";
    private static final String ACTION_UPDATE_UI = "com.seekbar.update.UI.ACTION_UPDATE_UI";
    private static final String ACTION_LYRIC = "com.music.lyric.position.ACTION_LYRIC";
    private BroadcastReceiver broadcastReceiver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lyrics, container, false);
        mLyricView = (LyricView) view.findViewById(R.id.lyricView);
        try{
            File sdCard = new File(STR_PATH);
            fileArrayList = LoadLyricSDcard.findLyrics(sdCard);

        }catch (Exception e){
            STR_PATH = Environment.getExternalStorageDirectory().getPath();
            File sdCard = new File(STR_PATH);
            fileArrayList = LoadLyricSDcard.findLyrics(sdCard);
        }

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction()) {
                    case ACTION_UPDATE_UI:
                        String mediaPosition = intent.getStringExtra("mediaPosition");
                        int position = Integer.parseInt(mediaPosition);
                        mLyricView.setCurrentTimeMillis(position);
                        break;
                    case ACTION_MUSIC_PLAY:
                        loadLyric(intent);
                        break;
                    case ACTION_START_MUSIC_PLAY:
                        loadLyric(intent);
                        break;

                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_MUSIC_PLAY));
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_START_MUSIC_PLAY));
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_UPDATE_UI));
        mLyricView.setOnPlayerClickListener(this);


        // ở dưới là code lyric của lib khác
//        mLyricView.setLyric(LyricUtils.parseLyric(getResources().openRawResource(R.raw.lyric_anhkhachayemkhac_khacviet), "UTF-8"));
//        mLyricView.setLyricIndex(0);
//        mLyricView.play();
        return view;
    }

    private void sendLyricPosition(int l){
        Intent servicePos = new Intent(ACTION_LYRIC);
        servicePos.putExtra("lyricPos",l);
        getActivity().sendBroadcast(servicePos);
    }

    private void loadLyric(Intent intent){ //?? intent

        String songData = intent.getStringExtra("songData");
//        songData = songData.replace(".mp3", ".lrc");
//        File file = new File(songData);
//        if(file.exists()){
//            mLyricView.reset();
//            mLyricView.setLyricFile(file);
//            mLyricView.invalidate();
//            Log.d(TAG, "Load lyric file: "+ file.getAbsolutePath());
//        }else{
//            mLyricView.reset();
//            Log.d(TAG, "Lyric not found: "+ file.getAbsolutePath());
//        }
//        mLyricView.reset();
        int indexSongData = songData.lastIndexOf("/");
        String subSongData = songData.substring(indexSongData+1).replace(".mp3","");
        for (int i = 0 ; i < fileArrayList.size(); i++){
            int indexFileArrayList = fileArrayList.get(i).toString().lastIndexOf("/");
            String FileArrayList = fileArrayList.get(i).toString().substring(indexFileArrayList+1).replace(".lrc","");
            if (subSongData.contains(FileArrayList)) {
                mLyricView.setLyricFile(fileArrayList.get(i));
                mLyricView.invalidate();
                return;
            }
        }
        mLyricView.setLyricFile(null);

    }

    @Override
    public void onPlayerClicked(long l, String s) {
        int position = (int) l;
        sendLyricPosition(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
