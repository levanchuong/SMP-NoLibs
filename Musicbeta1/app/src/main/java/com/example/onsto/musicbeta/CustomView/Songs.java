package com.example.onsto.musicbeta.CustomView;

/**
 * Created by onsto on 21/02/2017.
 */

public class Songs {
    public String nameSong;
    public String author;
//    public String itemName;
    public Integer position;
    private String songData;


    public Songs(String nameSong, String author, Integer position, String songData) {
        this.nameSong = nameSong;
        this.author = author;
//        this.itemName = itemName;
        this.position = position;
        this.songData = songData;
    }
    public String getSongData() {
        return songData;
    }

    public void setSongData(String songData) {
        this.songData = songData;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
//
//    public String getItemName() {
//        return itemName;
//    }
//
//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }
}
