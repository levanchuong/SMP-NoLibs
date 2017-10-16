package com.example.onsto.musicbeta;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Filter;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

import static com.example.onsto.musicbeta.FileSong.uri;

/**
 * Created by onsto on 30/03/2017.
 */

public class ServiceMusic extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener
                ,MediaPlayer.OnSeekCompleteListener{
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Uri uri;
    private FileSong fileSong = new FileSong();
    private BroadcastReceiver broadcastReceiver;
    private Intent seekBarIntent;
    private Handler handler = new Handler();
    private Handler handler2 = new Handler();
    private int mediaPosition, mediaMax;
    private Intent completeMusic;
    private static final String ACTION_START_MUSIC_PLAY = "com.broadcast.ACTION_MUSIC_PLAY";
    private static final String ACTION_MUSIC_PAUSE = "com.broadcast.service.music.ACTION_MUSIC_PAUSE";
    private static final String ACTION_MUSIC_PLAY = "com.broadcast.service.music.ACTION_MUSIC_PLAY";
    private static final String ACTION_PAUSE = "com.broadcast.service.ACTION_PAUSE";
    private static final String ACTION_RESUME = "com.broadcast.service.ACTION_RESUME";
    private static final String ACTION_SEEKBAR = "com.seekbar.sent.seekto.ACTION_SEEKBAR";
    private static final String ACTION_LYRIC = "com.music.lyric.position.ACTION_LYRIC";
    private static final String ACTION_UPDATE_UI = "com.seekbar.update.UI.ACTION_UPDATE_UI";
    private static final int NOTIFICATION_ID = 1;
    private Bitmap bm;
    private Drawable dr;
    private String songData;
    Notification status;
    @Override
    public void onCreate() {
        seekBarIntent = new Intent(ACTION_UPDATE_UI);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //tao notificaation
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case ACTION_MUSIC_PLAY:
                        try{
                            stopMedia();
                            int position = intent.getIntExtra("position", 0);
                            initNotification(position);
                            String path = FileSong.audioFilePathList.get(position).toString();
                            uri = Uri.parse(path);
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(path);
                            mediaPlayer.prepare();
                            MediaPlayer.create(getApplicationContext(), uri);
                            playMedia();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
//                    case "com.broadcast":
//                        try {
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        break;
                    case ACTION_MUSIC_PAUSE:
                        try{
                            stopMedia();
                            int position = intent.getIntExtra("position", 0);
                            initNotification(position);
                            String path = FileSong.audioFilePathList.get(position).toString();
                            uri = Uri.parse(path);
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(path);
                            mediaPlayer.prepare();
                            MediaPlayer.create(getApplicationContext(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    case ACTION_PAUSE:
                        pauseMedia();
                        break;
                    case ACTION_RESUME:
                        playMedia();
                        break;
                    case ACTION_SEEKBAR:
                        int seektoProgress = intent.getIntExtra("seektoProgress", 0);
                        mediaPlayer.seekTo(seektoProgress);
                        mediaMax = mediaPlayer.getDuration();
                        break;
                    case ACTION_LYRIC:
//                        String strPos = intent.getStringExtra("lyricPos");
                        int position = intent.getIntExtra("lyricPos",0);
                        mediaPlayer.seekTo(position);
                        break;
//                    case "com.seekbar.sent.position":
//                        int positionToNextIntent = intent.getIntExtra("positionToNextIntent", 0);
//                        mediaMax = mediaPlayer.getDuration();
//                        if (positionToNextIntent == mediaMax){
//                            Toast.makeText(context, "music completed service", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
                    default:
                        try {
                            stopMedia();
                            position = intent.getIntExtra("position", 0);
                            initNotification(position);
                            String path = FileSong.audioFilePathList.get(position).toString();
                            uri = Uri.parse(path);
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(path);
                            mediaPlayer.prepare();
                            MediaPlayer.create(getApplicationContext(), uri);
                            playMedia();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        };


        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_START_MUSIC_PLAY));
        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_MUSIC_PLAY));
        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_RESUME));
        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_PAUSE));
        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_MUSIC_PAUSE));
        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_SEEKBAR));
        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_LYRIC));

        setupHandle();

        return START_STICKY;

    }

    private void setupHandle() {
        handler.removeCallbacks(sendUpdateToUI);
        handler.postDelayed(sendUpdateToUI,1000);
//        handler2.removeCallbacks(sendUpdateToUI2);
//        handler2.postDelayed(sendUpdateToUI2, 1000);
    }

    private Runnable sendUpdateToUI = new Runnable() {
        @Override
        public void run() {
            sendMediaPosition();
            handler.postDelayed(sendUpdateToUI,1000);
        }
    };
//    private Runnable sendUpdateToUI2 = new Runnable() {
//        @Override
//        public void run() {
//            sendMediaPosition2();
//            handler.postDelayed(this, 1000);
//        }
//    };

    private void sendMediaPosition() {
        if (mediaPlayer != null) {
            mediaMax = mediaPlayer.getDuration();
            mediaPosition = mediaPlayer.getCurrentPosition();
            seekBarIntent.putExtra("mediaPosition", String.valueOf(mediaPosition));
            seekBarIntent.putExtra("mediaMax", String.valueOf(mediaMax));
            sendBroadcast(seekBarIntent);
        }
    }

//    private void sendMediaPosition2() {
//        if (mediaPlayer.isPlaying()) {
//            mediaPosition = mediaPlayer.getCurrentPosition();
//            seekBarIntent.putExtra("mediaPosition", String.valueOf(mediaPosition));
//            Log.d("TEST", "sendMediaPosition2: " + mediaPosition);
//            sendBroadcast(seekBarIntent);
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        else{
            handler.removeCallbacks(sendUpdateToUI);
            unregisterReceiver(broadcastReceiver);
            //cancel notification
            cancelNotification();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();
    }

    public void initNotification(int position){
        String ns = Context.NOTIFICATION_SERVICE;
        Context context = getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) getSystemService(ns);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification_control_layout);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);


//        button previous
        Intent btn_previous_intent = new Intent(ACTION_MUSIC_PLAY);
        songData = FileSong.arrayListSong.get(position).getSongData();
        btn_previous_intent.putExtra("position", position-1).putExtra("songData", songData);
        PendingIntent pen_btnPre_intent = PendingIntent.getBroadcast(this,0,btn_previous_intent,0);
//        builder.addAction(R.mipmap.ic_previous,"",pen_btnPre_intent);
        remoteViews.setOnClickPendingIntent(R.id.imageButtonPrevious_Notifi,pen_btnPre_intent);
        remoteViews.setImageViewResource(R.id.imageButtonPrevious_Notifi,
                R.mipmap.ic_previous);
        //button pause
        Intent btn_pause_intent = new Intent(ACTION_PAUSE);
        PendingIntent pen_btnPause_intent = PendingIntent.getBroadcast(this,0,btn_pause_intent,0);
        remoteViews.setOnClickPendingIntent(R.id.imageButtonPause_Play_Notifi,pen_btnPause_intent);
        remoteViews.setImageViewResource(R.id.imageButtonPause_Play_Notifi,
                R.mipmap.ic_pause);

//        builder.addAction(R.mipmap.ic_pause,"",pen_btnPause_intent);
        //button next
        Intent btn_next_intent = new Intent(ACTION_MUSIC_PLAY);
        songData = FileSong.arrayListSong.get(position).getSongData();
        btn_next_intent.putExtra("position", position+1).putExtra("songData", songData);
        PendingIntent pen_btnNext_intent = PendingIntent.getBroadcast(this,0,btn_next_intent,0);
        remoteViews.setOnClickPendingIntent(R.id.imageButtonNext_Notifi,pen_btnNext_intent);
        remoteViews.setImageViewResource(R.id.imageButtonNext_Notifi,
                R.mipmap.ic_next);
        Intent notifiIntent = new Intent(context,MainActivity.class);
        notifiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notifiIntent,0);
        for (int i = 0; i < FileSong.arrayListAlbum.size(); i++) {
            if (FileSong.arrayListSong.get(position).getAuthor().equals(FileSong.arrayListAlbum.get(i).getAlbumArtist())) {
                dr = FileSong.arrayListAlbum.get(i).getDrAlbum();
                remoteViews.setImageViewBitmap(R.id.imageViewLoadSong_Notifi,bm);
                CharSequence contentTitle = FileSong.arrayListSong.get(position).getNameSong();
                remoteViews.setTextViewText(R.id.textViewNameSong_Notifi,contentTitle);
                CharSequence contentText = FileSong.arrayListSong.get(position).getAuthor();
                remoteViews.setTextViewText(R.id.textViewArtist_Notifi,contentText);
                CharSequence ticketText = "SmartMusicPlayer";

                int icon = R.drawable.icon_player_black;
                builder.setSmallIcon(icon).setLargeIcon(bm).setCustomBigContentView(remoteViews)
                        .setContentIntent(pendingIntent).setAutoCancel(false)
                        .setPriority(Notification.PRIORITY_MAX).setTicker(ticketText)
                        .setOngoing(false);
//                status = new Notification.Builder(this).build();
//                status.contentView = remoteViews;
//                status.flags = Notification.FLAG_ONGOING_EVENT;
//                status.icon = icon;
//                status.contentIntent = pendingIntent;
                notificationManager.notify(NOTIFICATION_ID,builder.build());
//                startForeground(NOTIFICATION_ID, status);

//                bm = fileSong.arrayListAlbum.get(i).getBmAlbum();
//                int icon = R.drawable.icon_player_black;
//                CharSequence ticketText = "SmartMusicPlayer";
//                long when = System.currentTimeMillis();
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//                CharSequence contentTitle = fileSong.arrayListSong.get(position).getNameSong();
//                CharSequence contentText = fileSong.arrayListSong.get(position).getAuthor();
//
//                Intent notifiIntent = new Intent(this,MainActivity.class);
//                PendingIntent contentIntent = PendingIntent.getActivity(context,0,notifiIntent,0);
//                builder.setSmallIcon(icon).setLargeIcon(bm).setContentTitle(contentTitle).setContentText(contentText)
//                        .setContentIntent(contentIntent).setTicker(ticketText)
//                        .setWhen(when);
//                Notification notification = builder.getNotification();
//                notification.flags = Notification.FLAG_ONGOING_EVENT;
//                notificationManager.notify(NOTIFICATION_ID,notification);
                return;
            }
        }


    }
    public void cancelNotification(){
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) getSystemService(ns);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stopMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    public void createMusic(Intent intent){
        try{
        int position = intent.getIntExtra("position", 0);
        initNotification(position);
        String path = FileSong.audioFilePathList.get(position).toString();
        uri = Uri.parse(path);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepare();
        MediaPlayer.create(getApplicationContext(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}
