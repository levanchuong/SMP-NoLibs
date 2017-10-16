package com.example.onsto.musicbeta.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onsto.musicbeta.Adapter.AdapterRecyclerView;
import com.example.onsto.musicbeta.Adapter.AlbumAdapterRecyclerView;
import com.example.onsto.musicbeta.Adapter.ArtistAdapterRecyclerView;
import com.example.onsto.musicbeta.CustomView.Albums;
import com.example.onsto.musicbeta.CustomView.Artists;
import com.example.onsto.musicbeta.CustomView.Songs;
import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }
    FileSong fileSong = new FileSong();
    AdapterRecyclerView adapterRecyclerView;
    AlbumAdapterRecyclerView albumAdapterRecyclerView;
    ArtistAdapterRecyclerView artistAdapterRecyclerView;
    RecyclerView rvSearchSongs,rvSearchAlbums,rvSearchArtists;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        rvSearchSongs = (RecyclerView) view.findViewById(R.id.recyclerViewSearch_Songs);
        rvSearchAlbums = (RecyclerView) view.findViewById(R.id.recyclerViewSearch_Albums);
        rvSearchArtists = (RecyclerView) view.findViewById(R.id.recyclerViewSearch_Artist);



        return view;
    }
    public AdapterRecyclerView getAdapterRecyclerView(ArrayList<Songs> arrayListSong){
//        fileSong.arrayListSong.clear();
//        fileSong.audioFilePathList.clear();
        fileSong.ScanAllMusic(getActivity().getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapterRecyclerView = new AdapterRecyclerView(arrayListSong,getActivity());
        rvSearchSongs.setLayoutManager(layoutManager);
        rvSearchSongs.setAdapter(adapterRecyclerView);
        return this.adapterRecyclerView;
    }
    public AlbumAdapterRecyclerView getAlbumAdapterRecyclerView(ArrayList<Albums> arrayListAlbum){
        fileSong.ScanAllAlbum(getActivity().getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        albumAdapterRecyclerView = new AlbumAdapterRecyclerView(arrayListAlbum,getActivity());
        rvSearchAlbums.setLayoutManager(layoutManager);
        rvSearchAlbums.setAdapter(albumAdapterRecyclerView);
        return this.albumAdapterRecyclerView;
    }
    public ArtistAdapterRecyclerView getArtistAdapterRecyclerView(ArrayList<Artists> arrayListArtist){
        fileSong.ScanAllArtist(getActivity().getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        artistAdapterRecyclerView = new ArtistAdapterRecyclerView(arrayListArtist,getActivity());
        rvSearchArtists.setLayoutManager(layoutManager);
        rvSearchArtists.setAdapter(artistAdapterRecyclerView);
        return this.artistAdapterRecyclerView;
    }

}
