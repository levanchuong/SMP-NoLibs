package com.example.onsto.musicbeta.Fragment;


import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onsto.musicbeta.Adapter.AdapterRecyclerView;
import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment{

    RecyclerView rvSongs;

    FileSong fileSong = new FileSong();
    AdapterRecyclerView adapterRecyclerView;

    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        rvSongs = (RecyclerView) view.findViewById(R.id.recyclerViewSongs);
//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

//        FileSong plm = new FileSong();
        // get all songs from sdcard
//        songsList = plm.getPlayList();

//        mySongs = fileSong.findSongs(Environment.getExternalStorageDirectory());

//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        for (int i = 0; i < mySongs.size(); i++) {
//            uri[i] = Uri.parse(mySongs.get(i).toString());
//            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "");
//            mediaMetadataRetriever.setDataSource(getContext(),uri[i]);
//            String title = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
//            String artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
//            if (title != null ) {
//                titles[i] = title;
//            } else titles[i] = mySongs.get(i).getName().toString().replace(".mp3", "");
//            if (artist != null ) {
//                artists[i] = artist;
//            } else artists[i] = "Unknown";
//            Songs s = new Songs(titles[i],artists[i],items[i],i);
//            arrayListSong.add(s);
//        }

//        mediaMetadataRetriever.release();
//        Uri u = Uri.parse(mySongs.get(15).toString());
//        getTrackInfor(u);
        FileSong.arrayListSong.clear();
        FileSong.audioFilePathList.clear();
        fileSong.ScanAllMusic(getActivity().getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapterRecyclerView = new AdapterRecyclerView(FileSong.arrayListSong,getActivity());
        rvSongs.setLayoutManager(layoutManager);
        rvSongs.setAdapter(adapterRecyclerView);








        return view;

    }
    //click back again to exit

    public void getTrackInfor(Uri uri){
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(getContext(),uri);
        String t = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        Toast.makeText(getActivity(),t,Toast.LENGTH_LONG).show();
//        artist[] = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
    }
    public AdapterRecyclerView getAdapterRecyclerView(){
        return this.adapterRecyclerView;
    }


    }



