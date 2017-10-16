package com.example.onsto.musicbeta.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onsto.musicbeta.Adapter.SongBelongArtistAdapterRV;
import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongBelongArtistFragment extends Fragment {
    RecyclerView rvSongsBelongArtist;
    FileSong fileSong = new FileSong();
    SongBelongArtistAdapterRV songBelongArtistAdapterRV;

    public SongBelongArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_belong_artist, container, false);
        rvSongsBelongArtist = (RecyclerView) view.findViewById(R.id.recyclerViewSongsBelongArtist);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            String str = bundle.getString("nameSongOfArtist");
//            fileSong.arrayListSongBelongAlbum.clear();
//            fileSong.audioFilePathList2.clear();
            FileSong.arrayListSong.clear();
            FileSong.audioFilePathList.clear();
            fileSong.ScanAllMusicBelongArtist(getActivity(),str);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            songBelongArtistAdapterRV = new SongBelongArtistAdapterRV(FileSong.arrayListSong,getActivity());
            rvSongsBelongArtist.setLayoutManager(layoutManager);
            rvSongsBelongArtist.setAdapter(songBelongArtistAdapterRV);
        }



        return view;
    }

}
