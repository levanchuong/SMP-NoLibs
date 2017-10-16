package com.example.onsto.musicbeta.OldClass;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.R;
import com.example.onsto.musicbeta.Convert.Utilities;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Player_Menu extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    ImageButton ibPlay, ibPrevious, ibNext, ibBack, ibRepeat, ibShuffle,ibAlarm;
    TextView tvNameSong, tvCurrent, tvDead,tvArtist;
    int position, cdTime,timeToCd = 0;
    SeekBar sbMusic,sbAlarm;
    Uri uri;
    AudioPlay audioPlay;
    Animation animRotate, animtextview;
    ImageView imgLoadSong;
    Runnable runnable;
    android.os.Handler handler,cdHandler;
    Utilities utils;
    boolean isShuffle = false;
    boolean isRepeat = false;
    boolean isPause = false;
    FileSong fileSong;
    Dialog dialog;
    CountDownRunnable cdrunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_menu);

        handler = new android.os.Handler();
        cdHandler = new android.os.Handler();
        ibPlay = (ImageButton) findViewById(R.id.imageButtonPause_Play);
        ibNext = (ImageButton) findViewById(R.id.imageButtonNext);
        ibPrevious = (ImageButton) findViewById(R.id.imageButtonPrevious);
        ibBack = (ImageButton) findViewById(R.id.imageButtonBack);
        ibRepeat = (ImageButton) findViewById(R.id.imageButtonRepeat);
        ibAlarm = (ImageButton) findViewById(R.id.imageButtonAlarm);
        ibShuffle = (ImageButton) findViewById(R.id.imageButtonShuffle);
        tvNameSong = (TextView) findViewById(R.id.textViewNameSong);
        tvCurrent = (TextView) findViewById(R.id.textViewCurrent);
        tvArtist = (TextView) findViewById(R.id.textViewArtist) ;
        tvDead = (TextView) findViewById(R.id.textViewDead);
        sbMusic = (SeekBar) findViewById(R.id.seekBarSong);
        sbAlarm = (SeekBar) findViewById(R.id.seekBarAlarm);
        imgLoadSong = (ImageView) findViewById(R.id.imageViewLoadSong);

        utils = new Utilities();

        //load animation
        animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        imgLoadSong.startAnimation(animRotate);
        ibPlay.setOnClickListener(this);
        ibNext.setOnClickListener(this);
        ibPrevious.setOnClickListener(this);
        ibBack.setOnClickListener(this);
        ibRepeat.setOnClickListener(this);
        ibShuffle.setOnClickListener(this);
        ibAlarm.setOnClickListener(this);
        //tạo inten get thông tin từ putExtra
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        position = bundle.getInt("pos", 0);
        String file_Path = bundle.getString("file_path");

        uri = Uri.parse(file_Path);

//
//        animTranslateTexview();


        createAudio(file_Path, uri);
        playAudio();
        changeTitle_Artist(position);

        sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
        playCircle();
        timeText();
        cdrunnable = new CountDownRunnable();
        showCountDown();
        Log.e("Oncreate", "Oncreate: dang chay");

        AudioPlay.mediaPlayer.setOnCompletionListener(this);

        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                if (input) {
                    AudioPlay.mediaPlayer.seekTo(progress);
                    sbMusic.setProgress(progress);
                    timeText();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                timeText();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                audioPlay.mediaPlayer.seekTo(seekBar.getProgress());
                timeText();

            }
        });

    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imageButtonPause_Play:
                ibPlay.setImageResource(R.mipmap.ic_play);
                if (AudioPlay.mediaPlayer.isPlaying()) {
                    AudioPlay.mediaPlayer.pause();
                    isPause = true;
                    Toast.makeText(this, ""+isPause, Toast.LENGTH_SHORT).show();
                } else {
                    AudioPlay.mediaPlayer.start();
                    ibPlay.setImageResource(R.mipmap.ic_pause);
                    isPause = false;
                    Toast.makeText(this, ""+isPause, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imageButtonNext:
//                position = (position + 1) % mySongs.size();
                playSongWithCondition();
                break;
            case R.id.imageButtonPrevious:

//                position = (position - 1 < 0) ? mySongs.size() - 1 : position - 1;
                playSongWithCondition_Previous();
                break;
            case R.id.imageButtonRepeat:

                if (isRepeat) {
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat all", Toast.LENGTH_SHORT).show();
                    ibRepeat.setImageResource(R.mipmap.ic_loop_all);
                } else {
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat one", Toast.LENGTH_SHORT).show();

                    ibRepeat.setImageResource(R.drawable.loopone32);
                }

                break;
            case R.id.imageButtonShuffle:
                if (isShuffle) {
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    ibShuffle.setImageResource(R.drawable.disshuffle);
                } else {
                    // make repeat to true
                    isShuffle = true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false

                    ibShuffle.setImageResource(R.drawable.mediashuffle);

                }
                break;
            case R.id.imageButtonBack:
//                startActivity(new Intent(this,MainActivity.class));
                onBackPressed();
                break;
            case R.id.imageButtonAlarm:
                dialog = new Dialog(this);
                // khởi tạo dialog
                dialog.setContentView(R.layout.alarmdialog);
                // xét layout cho dialog
                dialog.setTitle("Hẹn giờ tắt nhạc");
                final SeekBar sbAlarm = (SeekBar) dialog.findViewById(R.id.seekBarAlarm);
                final TextView tvAlarm = (TextView) dialog.findViewById(R.id.textViewAlarm);
                sbAlarm.setMax(120);
                sbAlarm.setProgress(timeToCd);
                tvAlarm.setText("Tắt nhạc sau: " + timeToCd + " phút");
                sbAlarm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        tvAlarm.setText("Tắt nhạc sau: " + progress + " phút");
                        timeToCd = progress;
                        cdTime = timeToCd * 60;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                // xét tiêu đề cho dialog

                dialog.show();
                break;

        }
    }



    public void createAudio(String path,Uri uri) {
        if (AudioPlay.mediaPlayer != null && AudioPlay.mediaPlayer.isPlaying()) {
            AudioPlay.stopAudio();
        }
        AudioPlay.createAudio(getApplicationContext(),path, uri);
    }
    public void playAudio(){
        AudioPlay.playAudio();
    }

    public void playCircle() {
        sbMusic.setProgress(AudioPlay.mediaPlayer.getCurrentPosition());
        runnable = new Runnable() {
            @Override
            public void run() {


//                    tvCurrent.setText(timecurrent);
//                    tvExist.setText(deadtime);
                timeText();
                playCircle();
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void animTranslateTexview() {
        if (tvNameSong.length() > 25) {
            animtextview = AnimationUtils.loadAnimation(this, R.anim.anim_textview_sequence);
            animtextview.cancel();
            tvNameSong.startAnimation(animtextview);
        }
    }
//    @Override
//    public void onActivityResult(int requestCode,
//                                 int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == 100){
//            Uri uri = Uri.parse(String.valueOf(data.getExtras().getInt("pos")));
//            // play selected song
//            audioPlay.playAudio(getApplicationContext(),uri);
//        }
//
//    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playSongWithCondition();

    }

    public void playSongWithCondition() {
        if (isRepeat) {
            String file_path = FileSong.audioFilePathList.get(position);
            uri = Uri.parse(file_path);
            createAudio(file_path, uri);
            playAudio();
            sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
            sbMusic.setProgress(0);
            AudioPlay.mediaPlayer.setOnCompletionListener(this);
        } else if (isShuffle) {
            Random rand = new Random();
            position = rand.nextInt((FileSong.arrayListSong.size() - 1) - 0 + 1) + 0;
            String file_path = FileSong.audioFilePathList.get(position);
            uri = Uri.parse(file_path);
            changeTitle_Artist(position);
            createAudio(file_path, uri);
            playAudio();
            sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
            sbMusic.setProgress(0);
            AudioPlay.mediaPlayer.setOnCompletionListener(this);
        } else if (position < (FileSong.arrayListSong.size() - 1)) {
            if (isPause) {
                String file_path = FileSong.audioFilePathList.get(position + 1);
                uri = Uri.parse(file_path);
                changeTitle_Artist(position + 1);
                position = position + 1;
                createAudio(file_path, uri);
                sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
                sbMusic.setProgress(0);
                AudioPlay.mediaPlayer.setOnCompletionListener(this);
            }else{
                String file_path = FileSong.audioFilePathList.get(position + 1);
                uri = Uri.parse(file_path);
                changeTitle_Artist(position + 1);
                createAudio(file_path, uri);
                playAudio();
                position = position + 1;
                sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
                sbMusic.setProgress(0);
                AudioPlay.mediaPlayer.setOnCompletionListener(this);
            }
        } else {
            if (isPause){
                // play first song
                String file_path= FileSong.audioFilePathList.get(0);
                uri = Uri.parse(file_path);
                changeTitle_Artist(0);
                position = 0;
                createAudio(file_path, uri);
                sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
                sbMusic.setProgress(0);
                AudioPlay.mediaPlayer.setOnCompletionListener(this);
            }else {
                // play first song
                String file_path = FileSong.audioFilePathList.get(0);
                uri = Uri.parse(file_path);
                changeTitle_Artist(0);
                createAudio(file_path, uri);
                playAudio();
                position = 0;
                sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
                sbMusic.setProgress(0);
                AudioPlay.mediaPlayer.setOnCompletionListener(this);
            }
        }

    }
    public void playSongWithCondition_Previous(){
        if(isRepeat) {
            String file_path= FileSong.audioFilePathList.get(position);
            uri = Uri.parse(file_path);
            createAudio(file_path,uri);
            playAudio();
            sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
            sbMusic.setProgress(0);
            AudioPlay.mediaPlayer.setOnCompletionListener(this);
        }else if(isShuffle){
            Random rand = new Random();
            position = rand.nextInt((FileSong.arrayListSong.size() - 1) - 0 + 1) + 0;
            changeTitle_Artist(position);
            String file_path= FileSong.audioFilePathList.get(position);
            uri = Uri.parse(file_path);
            createAudio(file_path,uri);
            playAudio();
            sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
            sbMusic.setProgress(0);
            AudioPlay.mediaPlayer.setOnCompletionListener(this);
        }else if (position - 1 < 0) {
            if (isPause) {
                position = FileSong.arrayListSong.size() - 1;
                String file_path = FileSong.audioFilePathList.get(position);
                uri = Uri.parse(file_path);
                changeTitle_Artist(position);
                createAudio(file_path, uri);
                sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
                sbMusic.setProgress(0);
                AudioPlay.mediaPlayer.setOnCompletionListener(this);
            }else {
                position = FileSong.arrayListSong.size() - 1;
                String file_path = FileSong.audioFilePathList.get(position);
                uri = Uri.parse(file_path);
                changeTitle_Artist(position);
                createAudio(file_path, uri);
                playAudio();
                sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
                sbMusic.setProgress(0);
                AudioPlay.mediaPlayer.setOnCompletionListener(this);
            }
            } else {
                if (isPause) {
                    // play first song
                    position = position - 1;
                    String file_path = FileSong.audioFilePathList.get(position);
                    uri = Uri.parse(file_path);
                    changeTitle_Artist(position);
                    createAudio(file_path, uri);
                    sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
                    sbMusic.setProgress(0);
                    AudioPlay.mediaPlayer.setOnCompletionListener(this);
                }else {
                    // play first song
                    position = position - 1;
                    String file_path = FileSong.audioFilePathList.get(position);
                    uri = Uri.parse(file_path);
                    changeTitle_Artist(position);
                    createAudio(file_path, uri);
                    playAudio();
                    sbMusic.setMax(AudioPlay.mediaPlayer.getDuration());
                    sbMusic.setProgress(0);
                    AudioPlay.mediaPlayer.setOnCompletionListener(this);
                }
        }

    }
    public void changeTitle_Artist(int position){
        String nameSongChange = FileSong.arrayListSong.get(position).getNameSong();
        String artistChange = FileSong.arrayListSong.get(position).getAuthor();
        if(nameSongChange.length() > 25){
            nameSongChange = nameSongChange.substring(0,25)+"...";
        }
        tvNameSong.setText(nameSongChange);
        if(artistChange.length() > 25){
            artistChange = artistChange.substring(0,25)+"...";
        }
        tvArtist.setText(artistChange);
    }
    public void timeText() {
        Runnable mrunnable = new Runnable() {
            @Override
            public void run() {
                long totalDuration = AudioPlay.mediaPlayer.getDuration();
                long currentDuration = AudioPlay.mediaPlayer.getCurrentPosition();
                tvCurrent.setText(""+utils.milliSecondsToTimer(currentDuration));
                tvDead.setText(""+utils.milliSecondsToTimer(totalDuration) );
//                int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
                //Log.d("Progress", ""+progress);
//                sbMusic.setProgress(progress);

            }
        };
        handler.postDelayed(mrunnable,100);
    }
/// tạo hàm và class đếm ngược
    public void countDownHandle(){

            cdTime--;
        timeToCd = cdTime/60;
            if (cdTime == 0){
                AudioPlay.mediaPlayer.pause();
                ibPlay.setImageResource(R.mipmap.ic_play);

            }
        cdHandler.postDelayed(cdrunnable, 1000);
    }
    private class CountDownRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            countDownHandle();
        }

    }

    private void showCountDown() {
        cdHandler.removeCallbacks(cdrunnable);
        cdHandler.postDelayed(cdrunnable, 1000);
    }
    @Override
    public void onBackPressed() {

//            System.exit(0);
            super.onBackPressed();


    }

}