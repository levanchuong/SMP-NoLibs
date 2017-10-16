package com.example.onsto.musicbeta.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onsto.musicbeta.R;
import com.example.onsto.musicbeta.Adapter.ViewPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private TabLayout tabLayoutMain;
    private ViewPager viewPagerMain;
    private ViewPagerAdapter viewPagerAdapter;
    Toolbar toolbar = null;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        tabLayoutMain = (TabLayout) v.findViewById(R.id.tabLayoutMain);

        viewPagerMain = (ViewPager) v.findViewById(R.id.viewPagerMain);
        viewPagerAdapter = new ViewPagerAdapter (getChildFragmentManager());
        viewPagerAdapter.addFragment(new MenuFragment(), "Songs");
        viewPagerAdapter.addFragment(new AlbumFragment(), "Albums");
        viewPagerAdapter.addFragment(new ArtistFragment(), "Artists");
        viewPagerMain.setAdapter(viewPagerAdapter);
        tabLayoutMain.setupWithViewPager(viewPagerMain);
         return v;
    }

}
