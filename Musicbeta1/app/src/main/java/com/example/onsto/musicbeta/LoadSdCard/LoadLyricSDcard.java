package com.example.onsto.musicbeta.LoadSdCard;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Chuong on 17/04/2017.
 */

public class LoadLyricSDcard {
        public static ArrayList<File> findLyrics(File root) {
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                al.addAll(findLyrics(singleFile));
            } else {
                if (singleFile.getName().endsWith(".lrc") ) {
                    al.add(singleFile);
                    Log.d("tim_lyric", "findLyrics: "+singleFile.toString());
                }
            }
        }
        return al;

    }
}
