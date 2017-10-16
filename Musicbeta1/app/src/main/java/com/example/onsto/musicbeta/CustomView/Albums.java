package com.example.onsto.musicbeta.CustomView;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by onsto on 22/03/2017.
 */

public class Albums {
    public String albumName;
    public String albumArtist;
    public Integer imgAlbum;
    public Drawable drAlbum;
    public Bitmap bmAlbum;

//    public Bitmap getBmAlbum() {
//        return bmAlbum;
//    }
//
//    public void setBmAlbum(Bitmap bmAlbum) {
//        this.bmAlbum = bmAlbum;
//    }

    public Albums(String albumName, String albumArtist, Drawable drAlbum) {
        this.albumName = albumName;
        this.albumArtist = albumArtist;
        this.bmAlbum = bmAlbum;
        this.drAlbum = drAlbum;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public Drawable getDrAlbum() {
        return drAlbum;
    }

    public void setDrAlbum(Drawable album) {
        drAlbum = album;
    }
//
//    public Integer getImgAlbum() {
//        return imgAlbum;
//    }
//
//    public void setImgAlbum(Integer imgAlbum) {
//        this.imgAlbum = imgAlbum;
//    }
}
