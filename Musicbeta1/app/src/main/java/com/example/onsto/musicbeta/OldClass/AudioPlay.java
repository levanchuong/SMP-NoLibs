package com.example.onsto.musicbeta.OldClass;

import android.content.Context;
import android.graphics.Path;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AudioPlay {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static boolean isplayingAudio=false;
    public static String title,artist;
    public static void createAudio(Context c, String path,Uri uri) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            MediaPlayer.create(c,uri);
            isplayingAudio=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void playAudio(){
        mediaPlayer.start();
    }
    public static void stopAudio(){
        isplayingAudio=false;
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}
