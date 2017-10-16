package com.example.onsto.musicbeta.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.onsto.musicbeta.FileSong;
import com.example.onsto.musicbeta.MainActivity;
import com.example.onsto.musicbeta.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView tvCountSong,tvCountAlbum,tvCountArtist;
    LinearLayout llSongLibrary,llAlbum,llArtist;
    FileSong fileSong = new FileSong();
    FragmentManager fragmentManager;
    MenuFragment menuFragment;
    AlbumFragment albumFragment;
    ArtistFragment artistFragment;
    MainFragment mainFragment;
    LinearLayout llDragView;
    ImageView imgSongList;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        tvCountSong = (TextView) view.findViewById(R.id.textViewCountSong);
//        tvCountAlbum = (TextView) view.findViewById(R.id.textViewCountAlbum);
//        tvCountArtist = (TextView) view.findViewById(R.id.textViewCountArtist);
        llSongLibrary = (LinearLayout) view.findViewById(R.id.linearLayoutSongLibrary);
        imgSongList = (ImageView) view.findViewById(R.id.imageViewSongList);
        imgSongList.getBackground().setAlpha(255);
//        llAlbum = (LinearLayout) view.findViewById(R.id.linearLayoutAlbum) ;
//        llArtist = (LinearLayout) view.findViewById(R.id.linearLayoutArtist);
        llSongLibrary.setOnClickListener(this);
//        llAlbum.setOnClickListener(this);
//        llArtist.setOnClickListener(this);
        fileSong.ScanAllMusic(getActivity().getApplicationContext());
        fileSong.ScanAllAlbum(getActivity().getApplicationContext());
        fileSong.ScanAllArtist(getActivity().getApplicationContext());
//        tvCountSong.setText(""+fileSong.countSong);
//        tvCountAlbum.setText(""+fileSong.countAlbum);
//        tvCountArtist.setText(""+fileSong.countArtist);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.linearLayoutSongLibrary:
//                fragmentManager = getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                menuFragment = new MenuFragment();
//                fragmentTransaction.replace(R.id.fragment_container, menuFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Thư viện nhạc");
                mainFragment = new MainFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mainFragment);
                fragmentTransaction.commit();
                break;
//            case R.id.linearLayoutAlbum:
//                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Albums");
//                fragmentManager = getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                albumFragment = new AlbumFragment();
//                fragmentTransaction.replace(R.id.fragment_container, albumFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                break;
//            case R.id.linearLayoutArtist:
//                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Artists");
//                fragmentManager = getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                artistFragment = new ArtistFragment();
//                fragmentTransaction.replace(R.id.fragment_container, artistFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                break;
        }
    }
}
