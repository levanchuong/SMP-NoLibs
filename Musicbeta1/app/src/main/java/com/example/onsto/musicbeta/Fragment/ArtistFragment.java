package com.example.onsto.musicbeta.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onsto.musicbeta.Adapter.ArtistAdapterRecyclerView;
import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {

    ArtistAdapterRecyclerView artistAdapterRecyclerView;
    FileSong fileSong = new FileSong();
    RecyclerView rvArtist;
    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_artist, container, false);
        rvArtist = (RecyclerView) view.findViewById(R.id.recyclerViewArtists);
        FileSong.arrayListArtist.clear();
        fileSong.ScanAllArtist(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        artistAdapterRecyclerView = new ArtistAdapterRecyclerView(FileSong.arrayListArtist,getActivity());
        rvArtist.setLayoutManager(layoutManager);
        rvArtist.setAdapter(artistAdapterRecyclerView);

        return view;
    }
    public ArtistAdapterRecyclerView getArtistAdapterRecyclerView(){
        return this.artistAdapterRecyclerView;
    }
}
