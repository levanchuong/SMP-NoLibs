package com.example.onsto.musicbeta;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onsto.musicbeta.Adapter.AdapterRecyclerView;
import com.example.onsto.musicbeta.Adapter.AlbumAdapterRecyclerView;
import com.example.onsto.musicbeta.Adapter.ArtistAdapterRecyclerView;
import com.example.onsto.musicbeta.Adapter.ViewPagerPlayerMenuAdapter;
import com.example.onsto.musicbeta.Convert.Utilities;
import com.example.onsto.musicbeta.CustomView.Albums;
import com.example.onsto.musicbeta.CustomView.Artists;
import com.example.onsto.musicbeta.CustomView.Songs;
import com.example.onsto.musicbeta.Fragment.AlbumFragment;
import com.example.onsto.musicbeta.Fragment.ArtistFragment;
import com.example.onsto.musicbeta.Fragment.HomeFragment;
import com.example.onsto.musicbeta.Fragment.LyricsFragment;
import com.example.onsto.musicbeta.Fragment.MainPlayerMenuFragment;
import com.example.onsto.musicbeta.Fragment.MenuFragment;
import com.example.onsto.musicbeta.Fragment.PlaylistPlayingFragment;
import com.example.onsto.musicbeta.Fragment.SearchFragment;
import com.example.onsto.musicbeta.LoadSdCard.LoadLyricSDcard;
import com.example.onsto.musicbeta.OldClass.AudioPlay;
import com.example.onsto.musicbeta.OldClass.MyBroadcastReceiver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

//import cn.zhaiyifan.lyric.widget.LyricView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener, View.OnClickListener {
    private NavigationView navigationView = null;
    private Toolbar toolbar = null;
    private SearchView searchView = null;
    private MenuFragment menuFragment;
    private SearchFragment searchFragment;
    private HomeFragment homeFragment;
    private AlbumFragment albumFragment;
    private ArtistFragment artistFragment;
    private LinearLayout llSubControl;
    private LinearLayout llPlayMenu;
    private FrameLayout frameLayoutSearch;
    private Intent serviceIntent;
    private BroadcastReceiver broadcastReceiver, broadcastReceiverMusicComplete;
    private ImageButton ibPlay, ibPrevious, ibNext, ibBack, ibRepeat, ibShuffle, ibAlarm, ibPlay_sub, ibPrevious_sub, ibNext_sub;
    private TextView tvNameSong, tvCurrent, tvDead, tvArtist, tvNameSong_sub, tvArtist_sub, tvNameSongNavi, tvSearchSongs, tvSearchAlbums, tvSearchArtists;
    private int position, cdTime, timeToCd = 0;
    private int seekMax, seekProgress;
    private SeekBar sbMusic, sbAlarm;
    private AudioPlay audioPlay;
    private Animation animRotate, animtextview;
    private ImageView imgLoadSong, imgLoadSong_sub;
    private Runnable runnable;
    private android.os.Handler handler, cdHandler;
    private Utilities utils;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private boolean isPause = false;
    private boolean musicIsPlaying = false;
    private Dialog dialog;
    private CountDownRunnable cdrunnable;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isSetMax = false;
    private boolean isBroadcastIsRegister;
    private FileSong fileSong = new FileSong();
    private Animation animLayoutSequence, animLayoutSequenceDown;
    private ViewPager viewPagerMainPlayerMenu;
    private ViewPagerPlayerMenuAdapter viewPagerPlayerMenuAdapter;
    private AdapterRecyclerView adapterRecyclerView;
    private AlbumAdapterRecyclerView albumAdapterRecyclerView;
    private ArtistAdapterRecyclerView artistAdapterRecyclerView;
    private RecyclerView rvSearchSongs, rvSearchAlbums, rvSearchArtists;
    private ScrollView scrollViewSearch;
    private static final String ACTION_START_MUSIC_PLAY = "com.broadcast.ACTION_MUSIC_PLAY";
    private static final String ACTION_MUSIC_PAUSE = "com.broadcast.service.music.ACTION_MUSIC_PAUSE";
    private static final String ACTION_MUSIC_PLAY = "com.broadcast.service.music.ACTION_MUSIC_PLAY";
    private static final String ACTION_PAUSE = "com.broadcast.service.ACTION_PAUSE";
    private static final String ACTION_RESUME = "com.broadcast.service.ACTION_RESUME";
    private static final String ACTION_SEEKBAR = "com.seekbar.sent.seekto.ACTION_SEEKBAR";
    private static final String ACTION_UPDATE_UI = "com.seekbar.update.UI.ACTION_UPDATE_UI";
    private static final String TAG = "CheckBug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try {
            fileSong.ScanAllMusic(this);
            fileSong.ScanAllAlbum(this);
//        }catch(OutOfMemoryError e){
//            Log.d(TAG, "onCreate: "+e.getMessage());
//        }
        handler = new android.os.Handler();
        cdHandler = new android.os.Handler();
        ibPlay = (ImageButton) findViewById(R.id.imageButtonPause_Play);
        ibNext = (ImageButton) findViewById(R.id.imageButtonNext);
        ibPrevious = (ImageButton) findViewById(R.id.imageButtonPrevious);
        ibPlay_sub = (ImageButton) findViewById(R.id.imageButtonPause_Play_sub);
        ibNext_sub = (ImageButton) findViewById(R.id.imageButtonNext_sub);
        ibPrevious_sub = (ImageButton) findViewById(R.id.imageButtonPrevious_sub);
        ibBack = (ImageButton) findViewById(R.id.imageButtonBack);
        ibRepeat = (ImageButton) findViewById(R.id.imageButtonRepeat);
        ibAlarm = (ImageButton) findViewById(R.id.imageButtonAlarm);
        ibShuffle = (ImageButton) findViewById(R.id.imageButtonShuffle);
        tvNameSong = (TextView) findViewById(R.id.textViewNameSong);
        tvNameSong_sub = (TextView) findViewById(R.id.textViewNameSong_sub);
        tvCurrent = (TextView) findViewById(R.id.textViewCurrent);
        tvArtist = (TextView) findViewById(R.id.textViewArtist);
        tvArtist_sub = (TextView) findViewById(R.id.textViewArtist_sub);
        tvDead = (TextView) findViewById(R.id.textViewDead);
        tvSearchSongs = (TextView) findViewById(R.id.textViewSearch_Songs);
        tvSearchAlbums = (TextView) findViewById(R.id.textViewSearch_Albums);
        tvSearchArtists = (TextView) findViewById(R.id.textViewSearch_Artists);
        sbMusic = (SeekBar) findViewById(R.id.seekBarSong);
        sbAlarm = (SeekBar) findViewById(R.id.seekBarAlarm);
        imgLoadSong = (ImageView) findViewById(R.id.imageViewLoadSong);
        imgLoadSong_sub = (ImageView) findViewById(R.id.imageViewLoadSong_sub);
        viewPagerMainPlayerMenu = (ViewPager) findViewById(R.id.viewPagerMainPlayerMenu);
        rvSearchSongs = (RecyclerView) findViewById(R.id.recyclerViewSearch_Songs);
        rvSearchAlbums = (RecyclerView) findViewById(R.id.recyclerViewSearch_Albums);
        rvSearchArtists = (RecyclerView) findViewById(R.id.recyclerViewSearch_Artist);
        scrollViewSearch = (ScrollView) findViewById(R.id.scrollViewSearch);
        frameLayoutSearch = (FrameLayout) findViewById(R.id.frameLayoutSearch);
        serviceIntent = new Intent(this, ServiceMusic.class);
        utils = new Utilities();
        //tạo inten get thông tin từ putExtra


//
//        animTranslateTexview();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                position = intent.getIntExtra("position", 0);
                changeTitle_Artist(position);
                ImgLoadAnimation(position);
                broadCastIntent(position);

            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_START_MUSIC_PLAY));

//        broadcastReceiverMusicComplete = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                playSongWithCondition();
//                Toast.makeText(context, "music completed", Toast.LENGTH_SHORT).show();
//            }
//        };
//        registerReceiver(broadcastReceiverMusicComplete, new IntentFilter("com.music.complete"));
//        playCircle();
//        cdrunnable = new CountDownRunnable();
//        showCountDown();

//        unregisterReceiver(broadcastReceiver);

        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                if (input) {
                    Intent seekToIntent = new Intent(ACTION_SEEKBAR);
                    seekToIntent.putExtra("seektoProgress", progress);
                    sendBroadcast(seekToIntent);
                    registerReceiver(broadcastSongComplete, new IntentFilter(ACTION_UPDATE_UI));
                } else {
                    registerReceiver(broadcastSongComplete, new IntentFilter(ACTION_UPDATE_UI));
                }
//                Intent positionToNextIntent = new Intent("com.seekbar.sent.position");
//                positionToNextIntent.putExtra("positionToNextIntent", progress);
//                sendBroadcast(positionToNextIntent);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ibPlay.setOnClickListener(this);
        ibNext.setOnClickListener(this);
        ibPrevious.setOnClickListener(this);
        ibPlay_sub.setOnClickListener(this);
        ibNext_sub.setOnClickListener(this);
        ibPrevious_sub.setOnClickListener(this);
        ibBack.setOnClickListener(this);
        ibRepeat.setOnClickListener(this);
        ibShuffle.setOnClickListener(this);
        ibAlarm.setOnClickListener(this);
        llPlayMenu = (LinearLayout) findViewById(R.id.player__menu);
        llSubControl = (LinearLayout) findViewById(R.id.subControl);
        llSubControl.setOnClickListener(this);
        homeFragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
        fragmentTransaction.commit();

        menuFragment = new MenuFragment();
//        menuFragment.getAdapterRecyclerView();

        searchFragment = new SearchFragment();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false); ẩn title

        viewPagerPlayerMenuAdapter = new ViewPagerPlayerMenuAdapter(getSupportFragmentManager());
        viewPagerPlayerMenuAdapter.addFragment(new MainPlayerMenuFragment());
        viewPagerPlayerMenuAdapter.addFragment(new LyricsFragment());
        viewPagerPlayerMenuAdapter.addFragment(new PlaylistPlayingFragment());
        viewPagerMainPlayerMenu.setOffscreenPageLimit(2);
        viewPagerMainPlayerMenu.setAdapter(viewPagerPlayerMenuAdapter);

        cdrunnable = new CountDownRunnable();
        showCountDown();

        //load data tu internet
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadStringFromUrl().execute("http://khoapham.vn");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);


        View header = navigationView.getHeaderView(0);
        tvNameSongNavi = (TextView) header.findViewById(R.id.textViewNameSongNavi);


        /// chỗ này để code tv thay đổi theo nhạc.

        Menu mnu = navigationView.getMenu();

        mnu.findItem(R.id.nav_share).setVisible(false);

        // find MenuItem you want to change
        MenuItem nav_home = mnu.findItem(R.id.nav_home);
        MenuItem nav_songs = mnu.findItem(R.id.nav_songs);
        MenuItem nav_album = mnu.findItem(R.id.nav_album);
        MenuItem nav_artist = mnu.findItem(R.id.nav_artist);
//        nav_gallery.setIcon(R.drawable.setting_20);
        MenuItem nav_send = mnu.findItem(R.id.nav_send);
//        nav_send.setIcon(R.drawable.about_20);
        nav_send.setTitle("About");

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
//            System.exit(0);
            super.onBackPressed();
//            finish();
            return;
        } else {

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the  menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
//        menu.findItem(R.id.action_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.action_search:
//                        searchFragment = new SearchFragment();
//                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment_container, searchFragment);
//                        fragmentTransaction.commit();
//                        break;
//                }
//                return true;
//            }
//        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                frameLayoutSearch.setVisibility(View.VISIBLE);
                tvSearchAlbums.setVisibility(View.GONE);
                tvSearchSongs.setVisibility(View.GONE);
                tvSearchArtists.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                frameLayoutSearch.setVisibility(View.GONE);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//    @SuppressWarnings("StatementWithEmptyBody")
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle("Home");
            homeFragment = new HomeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_songs) {
            getSupportActionBar().setTitle("Songs");
            menuFragment = new MenuFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, menuFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_album) {
            getSupportActionBar().setTitle("Albums");
            albumFragment = new AlbumFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, albumFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_artist) {
            getSupportActionBar().setTitle("Artists");
            artistFragment = new ArtistFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, artistFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Songs> arraySongs = new ArrayList<Songs>();
        ArrayList<Albums> arrayAlbums = new ArrayList<Albums>();
        ArrayList<Artists> arrayArtists = new ArrayList<Artists>();
        for (Songs songs : FileSong.arrayListSong) {
            String name = songs.getNameSong();
            if (name.contains(newText) && arraySongs.size() < 3)
                arraySongs.add(songs);
            if (newText.equals("")) {
                arraySongs.clear();
                tvSearchSongs.setVisibility(View.GONE);
            }
        }
        for (Albums albums : FileSong.arrayListAlbum) {
            String name = albums.getAlbumName();
            if (name.contains(newText) && arrayAlbums.size() < 3)
                arrayAlbums.add(albums);
            if (newText.equals("")) {
                arrayAlbums.clear();
                tvSearchAlbums.setVisibility(View.GONE);
            }
        }
        for (Artists artists : FileSong.arrayListArtist) {
            String name = artists.getArtist();
            if (name.contains(newText) && arrayArtists.size() < 3)
                arrayArtists.add(artists);
            if (newText.equals("")) {
                arrayArtists.clear();
                tvSearchArtists.setVisibility(View.GONE);
            }
        }
        try {
            FileSong.arrayListSong.clear();
            FileSong.arrayListAlbum.clear();
            FileSong.arrayListArtist.clear();
            if (arrayArtists.isEmpty()) {
                tvSearchArtists.setVisibility(View.VISIBLE);
                tvSearchArtists.setText("Artists : \n \t No Data");
                getArtistAdapterRecyclerView(FileSong.arrayListArtist).setFilter(arrayArtists);
            } else {
                tvSearchArtists.setText("Artists : ");
                tvSearchArtists.setVisibility(View.VISIBLE);
                getArtistAdapterRecyclerView(FileSong.arrayListArtist).setFilter(arrayArtists);
            }
            if (arraySongs.isEmpty()) {
                tvSearchSongs.setVisibility(View.VISIBLE);
                tvSearchSongs.setText("Songs : \n \t No Data");
                getAdapterRecyclerView(FileSong.arrayListSong).setFilter(arraySongs);
            } else {
                tvSearchSongs.setText("Songs : ");
                tvSearchSongs.setVisibility(View.VISIBLE);
                getAdapterRecyclerView(FileSong.arrayListSong).setFilter(arraySongs);
            }
            if (arrayAlbums.isEmpty()) {
                tvSearchAlbums.setVisibility(View.VISIBLE);
                tvSearchAlbums.setText("Albums : \n \t No Data");
                getAlbumAdapterRecyclerView(FileSong.arrayListAlbum).setFilter(arrayAlbums);
            } else {
                tvSearchAlbums.setText("Albums : ");
                tvSearchAlbums.setVisibility(View.VISIBLE);
                getAlbumAdapterRecyclerView(FileSong.arrayListAlbum).setFilter(arrayAlbums);
            }
            if (arrayAlbums.isEmpty() && arraySongs.isEmpty() && arrayArtists.isEmpty()) {
                tvSearchAlbums.setVisibility(View.GONE);
                tvSearchSongs.setVisibility(View.GONE);
                tvSearchArtists.setVisibility(View.GONE);
                getAlbumAdapterRecyclerView(FileSong.arrayListAlbum).setFilter(arrayAlbums);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.subControl:
                animLayoutSequence = AnimationUtils.loadAnimation(this, R.anim.anim_layout_sequence);
                llPlayMenu.startAnimation(animLayoutSequence);
                llPlayMenu.setVisibility(View.VISIBLE);
                break;
            case R.id.imageButtonPause_Play:
                if (musicIsPlaying) {
                    isPause = false;
                    musicIsPlaying = false;
                    broadCastResume();
                    ibPlay.setImageResource(R.mipmap.ic_pause);
                    ibPlay_sub.setImageResource(R.drawable.ic_pause_black);
                } else {
                    isPause = true;
                    musicIsPlaying = true;
                    broadCastPause();
                    ibPlay.setImageResource(R.mipmap.ic_play);
                    ibPlay_sub.setImageResource(R.drawable.ic_play_black);
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
            case R.id.imageButtonPause_Play_sub:
                if (musicIsPlaying) {
                    isPause = false;
                    musicIsPlaying = false;
                    broadCastResume();
                    ibPlay.setImageResource(R.mipmap.ic_pause);
                    ibPlay_sub.setImageResource(R.drawable.ic_pause_black);
                } else {
                    isPause = true;
                    musicIsPlaying = true;
                    broadCastPause();
                    ibPlay.setImageResource(R.mipmap.ic_play);
                    ibPlay_sub.setImageResource(R.drawable.ic_play_black);
                }
                break;
            case R.id.imageButtonNext_sub:
                playSongWithCondition();
                break;
            case R.id.imageButtonPrevious_sub:
                playSongWithCondition_Previous();
                break;
            case R.id.imageButtonRepeat:

                if (isRepeat) {
                    isRepeat = false;
                    ibRepeat.setImageResource(R.mipmap.ic_loop_all);
                } else {
                    // make repeat to true
                    isRepeat = true;

                    ibRepeat.setImageResource(R.drawable.loopone32);
                }

                break;
            case R.id.imageButtonShuffle:
                if (isShuffle) {
                    isShuffle = false;
                    ibShuffle.setImageResource(R.drawable.disshuffle);
                } else {
                    // make repeat to true
                    isShuffle = true;
                    // make shuffle to false

                    ibShuffle.setImageResource(R.drawable.mediashuffle);

                }
                break;
            case R.id.imageButtonBack:
                animLayoutSequenceDown = AnimationUtils.loadAnimation(this, R.anim.anim_layout_sequence_down);
                llPlayMenu.startAnimation(animLayoutSequenceDown);
                searchView.onActionViewCollapsed();
                llPlayMenu.setVisibility(View.GONE);
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

    public void playCircle() {
        sbMusic.setProgress(AudioPlay.mediaPlayer.getCurrentPosition());
        runnable = new Runnable() {
            @Override
            public void run() {

                playCircle();
            }
        };
        handler.postDelayed(runnable, 1000);
    }


    public void broadCastIntent(int positionOfSong) {
        Intent intent = new Intent(ACTION_MUSIC_PLAY);
        String songData = FileSong.arrayListSong.get(positionOfSong).getSongData();
        intent.putExtra("position", positionOfSong).putExtra("songData", songData);
        sendBroadcast(intent);
    }

    public void broadCastIntentMusic_Pause(int positionOfSong) {
        Intent intent = new Intent(ACTION_MUSIC_PAUSE);
        String songData = FileSong.arrayListSong.get(positionOfSong).getSongData();
        intent.putExtra("position", positionOfSong).putExtra("songData", songData);
        sendBroadcast(intent);
    }

    public void broadCastPause() {
        Intent intent = new Intent(ACTION_PAUSE);
        sendBroadcast(intent);
    }

    public void broadCastResume() {
        Intent intent = new Intent(ACTION_RESUME);
        sendBroadcast(intent);
    }

    public void playSongWithCondition() {
        if (isRepeat) {
            if (isPause)
                broadCastIntentMusic_Pause(position);
            else broadCastIntent(position);
        } else if (isShuffle) {
            if (isPause){
                Random rand = new Random();
                position = rand.nextInt((FileSong.arrayListSong.size() - 1) - 0 + 1) + 0;
                broadCastIntentMusic_Pause(position);
                changeTitle_Artist(position);
                ImgLoadAnimation(position);
            }else {
                Random rand = new Random();
                position = rand.nextInt((FileSong.arrayListSong.size() - 1) - 0 + 1) + 0;
                broadCastIntent(position);
                changeTitle_Artist(position);
                ImgLoadAnimation(position);
            }
        } else if (position < (FileSong.arrayListSong.size() - 1)) {
            if (isPause) {
                changeTitle_Artist(position + 1);
                broadCastIntentMusic_Pause(position + 1);
                ImgLoadAnimation(position+1);
                position = position + 1;
            } else {
                changeTitle_Artist(position + 1);
                broadCastIntent(position + 1);
                ImgLoadAnimation(position+1);
                position = position + 1;
            }
        } else {
            if (isPause) {
                // play first song
                changeTitle_Artist(0);
                broadCastIntentMusic_Pause(0);
                ImgLoadAnimation(0);
                position = 0;
            } else {
                // play first song

                changeTitle_Artist(0);
                broadCastIntent(0);
                ImgLoadAnimation(0);
                position = 0;
            }
        }
    }

    public void playSongWithCondition_Previous() {
        if (isRepeat) {
            if (isPause)
                broadCastIntentMusic_Pause(position);
            else broadCastIntent(position);
        } else if (isShuffle) {
            if (isPause){
                Random rand = new Random();
                position = rand.nextInt((FileSong.arrayListSong.size() - 1) - 0 + 1) + 0;
                changeTitle_Artist(position);
                broadCastIntentMusic_Pause(position);
                ImgLoadAnimation(position);
            }else {
                Random rand = new Random();
                position = rand.nextInt((FileSong.arrayListSong.size() - 1) - 0 + 1) + 0;
                changeTitle_Artist(position);
                broadCastIntent(position);
                ImgLoadAnimation(position);
            }
        } else if (position - 1 < 0) {
            if (isPause) {
                position = FileSong.arrayListSong.size() - 1;
                changeTitle_Artist(position);
                broadCastIntentMusic_Pause(position);
                ImgLoadAnimation(position);
            } else {
                position = FileSong.arrayListSong.size() - 1;
                changeTitle_Artist(position);
                broadCastIntent(position);
                ImgLoadAnimation(position);
//                audioPlay.mediaPlayer.setOnCompletionListener(this);
            }
        } else {
            if (isPause) {
                // play first song

                changeTitle_Artist(position - 1);
                broadCastIntentMusic_Pause(position - 1);
                position = position - 1;
            } else {
                // play first song

                changeTitle_Artist(position - 1);
                broadCastIntent(position - 1);
                ImgLoadAnimation(position-1);
                position = position - 1;
            }
        }

    }

    public void ImgLoadAnimation(int position) {
        //load animation
        for (int i = 0; i < FileSong.arrayListAlbum.size(); i++) {
            if (FileSong.arrayListSong.get(position).getAuthor().equals(FileSong.arrayListAlbum.get(i).getAlbumArtist())) {
//                imgLoadSong.setImageBitmap(fileSong.arrayListAlbum.get(i).getBmAlbum());
                animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
//                imgLoadSong.startAnimation(animRotate);
                imgLoadSong_sub.setImageDrawable(FileSong.arrayListAlbum.get(i).getDrAlbum());
//                imgLoadSong_sub.startAnimation(animRotate);
                return;
            }


        }
        //change loadImage if not include array album.
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_player_black);
//        imgLoadSong.setImageBitmap(myBitmap);
        animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
//        imgLoadSong.startAnimation(animRotate);
        imgLoadSong_sub.setImageBitmap(myBitmap);
        imgLoadSong_sub.startAnimation(animRotate);
    }

    public void changeTitle_Artist(int position) {
        String nameSongChange = FileSong.arrayListSong.get(position).getNameSong();
        String artistChange = FileSong.arrayListSong.get(position).getAuthor();
        if (nameSongChange.length() > 12) {
            nameSongChange = nameSongChange.substring(0, 12) + "...";
        }
        tvNameSong.setText(nameSongChange);
        tvNameSong_sub.setText(nameSongChange);
        if (artistChange.length() > 12) {
            artistChange = artistChange.substring(0, 12) + "...";
        }
        tvArtist.setText(artistChange);
        tvArtist_sub.setText(artistChange);
    }


    public BroadcastReceiver broadcastSongComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            songComplete(intent);
        }
    };

    public void songComplete(Intent i) {
        String mediaMax = i.getStringExtra("mediaMax");
        int max = Integer.parseInt(mediaMax);
        String mediaPosition = i.getStringExtra("mediaPosition");
        int current = Integer.parseInt(mediaPosition);
        if (current == max) {
            playSongWithCondition();
        }

    }

    public BroadcastReceiver broadcastReceiverSeekBar = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };
    public BroadcastReceiver broadcastReceiverTimeText = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTimeText(intent);
        }
    };

    public void updateUI(Intent i) {
        String mediaMax = i.getStringExtra("mediaMax");
        seekMax = Integer.parseInt(mediaMax);
        sbMusic.setMax(seekMax);
        tvDead.setText("" + utils.milliSecondsToTimer(seekMax));
        String mediaPosition = i.getStringExtra("mediaPosition");
        seekProgress = Integer.parseInt(mediaPosition);
        sbMusic.setProgress(seekProgress);
        tvCurrent.setText("" + utils.milliSecondsToTimer(seekProgress));
    }

    public void updateTimeText(Intent i) {
        String mediaMax = i.getStringExtra("mediaMax");
        seekMax = Integer.parseInt(mediaMax);
        tvDead.setText("" + utils.milliSecondsToTimer(seekMax));
        String mediaPosition = i.getStringExtra("mediaPosition");
        seekProgress = Integer.parseInt(mediaPosition);
        tvCurrent.setText("" + utils.milliSecondsToTimer(seekProgress));
    }

    /// tạo hàm và class đếm ngược
    public void countDownHandle() {

        cdTime--;
        timeToCd = cdTime / 60;
        if (cdTime == 0) {
            broadCastPause();
            ibPlay.setImageResource(R.mipmap.ic_play);

        }
        cdHandler.postDelayed(cdrunnable, 1000);
    }


    public class CountDownRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            countDownHandle();
        }

    }


    public void showCountDown() {
        cdHandler.removeCallbacks(cdrunnable);
        cdHandler.postDelayed(cdrunnable, 1000);
    }

    public AdapterRecyclerView getAdapterRecyclerView(ArrayList<Songs> arrayListSong) {
//        fileSong.arrayListSong.clear();
//        fileSong.audioFilePathList.clear();
        fileSong.ScanAllMusic(getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapterRecyclerView = new AdapterRecyclerView(arrayListSong, this);
        rvSearchSongs.setLayoutManager(layoutManager);
        rvSearchSongs.setAdapter(adapterRecyclerView);
        return this.adapterRecyclerView;
    }

    public AlbumAdapterRecyclerView getAlbumAdapterRecyclerView(ArrayList<Albums> arrayListAlbum) {
        fileSong.ScanAllAlbum(getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        albumAdapterRecyclerView = new AlbumAdapterRecyclerView(arrayListAlbum, this);
        rvSearchAlbums.setLayoutManager(layoutManager);
        rvSearchAlbums.setAdapter(albumAdapterRecyclerView);
        return this.albumAdapterRecyclerView;
    }

    public ArtistAdapterRecyclerView getArtistAdapterRecyclerView(ArrayList<Artists> arrayListArtist) {
        fileSong.ScanAllArtist(getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        artistAdapterRecyclerView = new ArtistAdapterRecyclerView(arrayListArtist, this);
        rvSearchArtists.setLayoutManager(layoutManager);
        rvSearchArtists.setAdapter(artistAdapterRecyclerView);
        return this.artistAdapterRecyclerView;
    }

    public class LoadStringFromUrl extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
//            String content = getXmlFromUrl(params[0]);
//            return content;
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
        }
    }

    public String getXmlFromUrl(String urlString) {
        String xml = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlString);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public void savingPreferences() {
        SharedPreferences preferences = getSharedPreferences("musicData", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("shuffle", isShuffle);
        editor.putBoolean("repeat", isRepeat);
        editor.commit();
    }

    public void restoringPreferences() {
        SharedPreferences preferences = getSharedPreferences("musicData", MODE_PRIVATE);
        isShuffle = preferences.getBoolean("shuffle", true);
        isRepeat = preferences.getBoolean("repeat", true);
        if (isRepeat) {
            isRepeat = true;
            ibRepeat.setImageResource(R.drawable.loopone32);
        } else {
            // make repeat to true
            isRepeat = false;
            ibRepeat.setImageResource(R.mipmap.ic_loop_all);
        }
        if (isShuffle) { // make repeat to true
            isShuffle = true;
            // make shuffle to false
            ibShuffle.setImageResource(R.drawable.mediashuffle);
        } else {
            isShuffle = false;
            ibShuffle.setImageResource(R.drawable.disshuffle);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastSongComplete);
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
        if (isBroadcastIsRegister) {
            try {
                unregisterReceiver(broadcastReceiverSeekBar);
                isBroadcastIsRegister = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        restoringPreferences();
        if (!isBroadcastIsRegister) {
            registerReceiver(broadcastReceiverSeekBar, new IntentFilter(ACTION_UPDATE_UI));
            isBroadcastIsRegister = true;

        }
    }
}
