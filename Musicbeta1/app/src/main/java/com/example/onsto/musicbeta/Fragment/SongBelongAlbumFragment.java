package com.example.onsto.musicbeta.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onsto.musicbeta.Adapter.AlbumAdapterRecyclerView;
import com.example.onsto.musicbeta.Adapter.SongBelongAlbumAdapterRV;
import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongBelongAlbumFragment extends Fragment {

    RecyclerView rvSongsBelongAlbum;
    FileSong fileSong = new FileSong();
    SongBelongAlbumAdapterRV songBelongAlbumAdapterRV;
    AlbumAdapterRecyclerView albumAdapterRecyclerView;
    public SongBelongAlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_belong_album, container, false);
        rvSongsBelongAlbum = (RecyclerView) view.findViewById(R.id.recyclerViewSongsBelongAlbum);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            String str = bundle.getString("nameSongOfAlbum");
//            fileSong.arrayListSongBelongAlbum.clear();
//            fileSong.audioFilePathList2.clear();
            FileSong.arrayListSong.clear();
            FileSong.audioFilePathList.clear();
            fileSong.ScanAllMusicBelongAlbum(getActivity(),str);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            songBelongAlbumAdapterRV = new SongBelongAlbumAdapterRV(FileSong.arrayListSong,getActivity());
            rvSongsBelongAlbum.setLayoutManager(layoutManager);
            rvSongsBelongAlbum.setAdapter(songBelongAlbumAdapterRV);
        }



        return view;
    }
}
