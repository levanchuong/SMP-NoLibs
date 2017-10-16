package com.example.onsto.musicbeta.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onsto.musicbeta.Adapter.AlbumAdapterRecyclerView;
import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    AlbumAdapterRecyclerView albumAdapterRecyclerView;
    FileSong fileSong = new FileSong();
    RecyclerView rvAlbums;
    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_album, container, false);


        rvAlbums = (RecyclerView)  view.findViewById(R.id.recyclerViewAlbum);
        FileSong.arrayListAlbum.clear();
        fileSong.ScanAllAlbum(getActivity());
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        albumAdapterRecyclerView = new AlbumAdapterRecyclerView(FileSong.arrayListAlbum,getActivity());
        rvAlbums.setLayoutManager(gridLayoutManager);
        rvAlbums.setAdapter(albumAdapterRecyclerView);
        return view;
    }
    public AlbumAdapterRecyclerView getAlbumAdapterRecyclerView(){
        return this.albumAdapterRecyclerView;
    }
}
