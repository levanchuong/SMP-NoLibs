package com.example.onsto.musicbeta;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.onsto.musicbeta.CustomView.Albums;
import com.example.onsto.musicbeta.CustomView.Artists;
import com.example.onsto.musicbeta.CustomView.Songs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by onsto on 03/03/2017.
 */
public class FileSong {
    public static ArrayList<Songs> arrayListSong = new ArrayList<Songs>();
    public static ArrayList<String> audioFilePathList = new ArrayList<>();
    public static ArrayList<Albums> arrayListAlbum = new ArrayList<Albums>();
    public static ArrayList<Artists> arrayListArtist = new ArrayList<Artists>();
    //    public static ArrayList<Bitmap> arrayListAlbumArt = new ArrayList<Bitmap>();
    public static Uri[] uri;
    public static int countSong;
    public static int countAlbum;
    public static int countArtist;
    public static int MAX = 25;
    private Albums albums;


//    public static ArrayList<File> findSongs(File root) {
//        ArrayList<File> al = new ArrayList<File>();
//        File[] files = root.listFiles();
//        for (File singleFile : files) {
//            if (singleFile.isDirectory() && !singleFile.isHidden()) {
//                al.addAll(findSongs(singleFile));
//            } else {
//                if (singleFile.getName().endsWith(".mp3") &&
//                        !singleFile.getName().endsWith("zing.mp3")) {
//                    al.add(singleFile);
//                }
//            }
//        }
//        return al;
//
//    }

    public void ScanAllMusic(Context c) {
        try {
            ContentResolver contentResolver = c.getContentResolver();
            String[] projection = {
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.DATA};

//                String where = MediaStore.Audio.Media.TITLE + " LIKE ?";
//                String[] params = new String[] { "%life%" };
            String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
            Cursor q = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection, MediaStore.Audio.Media.IS_MUSIC + "=1", null, sortOrder);
            if (q.moveToFirst()) {
                int title = q.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artist = q.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int position = 0;
                countSong = q.getCount();

                do {
                    String path = q.getString(q.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String subArtist = q.getString(artist);
                    String songData = q.getString(q.getColumnIndex(MediaStore.Audio.Media.DATA));
                    Songs s = new Songs(q.getString(title), subArtist, position, songData);
                    arrayListSong.add(s);
                    audioFilePathList.add(path);
                    position++;
//                            audioFileTitle.add(q.getString(title));
//                            audioFileArtist.add(q.getString(artist));
                } while (q.moveToNext());
            } else Toast.makeText(c, "No Song To Add", Toast.LENGTH_SHORT).show();
//
        } catch (Exception e) {
            Log.d("LOI", "ScanAllMusic: " + e.getMessage());
        }
    }

    public void ScanAllMusicBelongAlbum(Context c, String temp) {
        try {
            ContentResolver contentResolver = c.getContentResolver();
            String[] projection = {
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.DATA};

            String where = MediaStore.Audio.Media.ALBUM + " LIKE ?";
            String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
            String[] params = new String[]{temp};
            Cursor q = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection, where, params, sortOrder);
            if (q.moveToFirst()) {
                int title = q.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artist = q.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int position = 0;
//                countSong = q.getCount();

                do {

                    String path = q.getString(q.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String subArtist = q.getString(artist);
                    String songData = q.getString(q.getColumnIndex(MediaStore.Audio.Media.DATA));
                    Songs s = new Songs(q.getString(title), subArtist, position, songData);
                    arrayListSong.add(s);
                    audioFilePathList.add(path);
                    position++;
//                            audioFileTitle.add(q.getString(title));
//                            audioFileArtist.add(q.getString(artist));
                } while (q.moveToNext());
            } else Toast.makeText(c, "No Song To Add", Toast.LENGTH_SHORT).show();
//
        } catch (Exception e) {
            Log.d("LOI", "ScanAllMusic: " + e.getMessage());
        }
    }

    public void ScanAllMusicBelongArtist(Context c, String temp) {
        try {
            ContentResolver contentResolver = c.getContentResolver();
            String[] projection = {
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.DATA};

            String where = MediaStore.Audio.Media.ARTIST + " LIKE ?";
            String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
            String[] params = new String[]{temp};
            Cursor q = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection, where, params, sortOrder);
            if (q.moveToFirst()) {
                int title = q.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artist = q.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int position = 0;
//                countSong = q.getCount();

                do {

                    String path = q.getString(q.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String subArtist = q.getString(artist);
                    String songData = q.getString(q.getColumnIndex(MediaStore.Audio.Media.DATA));
                    Songs s = new Songs(q.getString(title), subArtist, position, songData);
                    arrayListSong.add(s);
                    audioFilePathList.add(path);
                    position++;
//                            audioFileTitle.add(q.getString(title));
//                            audioFileArtist.add(q.getString(artist));
                } while (q.moveToNext());
            } else Toast.makeText(c, "No Song To Add", Toast.LENGTH_SHORT).show();
//
        } catch (Exception e) {
            Log.d("LOI", "ScanAllMusic: " + e.getMessage());
        }
    }

    public void ScanAllAlbum(Context c) {
        try {
            ContentResolver contentResolver = c.getContentResolver();
            String[] projection = {
                    MediaStore.Audio.Albums.ALBUM,
                    MediaStore.Audio.Albums.ARTIST,
                    MediaStore.Audio.Albums.ALBUM_KEY,
                    MediaStore.Audio.Albums._ID,
                    MediaStore.Audio.Albums.ALBUM_ART,
                    MediaStore.Audio.Albums.NUMBER_OF_SONGS};
            String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";
//                String where = MediaStore.Audio.Media.TITLE + " LIKE ?";
//                String[] params = new String[] { "%life%" };
            Cursor q = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    projection, null, null, sortOrder);
            if (q.moveToFirst()) {
                int album = q.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                int artist = q.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
                int art = q.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                countAlbum = q.getCount();


                do {
                    String coverPath = q.getString(art);
                    if (coverPath != null) {
//                            Drawable img = Drawable.createFromPath(coverPath); cách này là đổi sang drawable
                        //cách này đổi sang bitmap.
                        File imgFile = new File(coverPath);
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inJustDecodeBounds = true;
//                        InputStream inputStream = new FileInputStream(imgFile);
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = 8;
//                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                        Bitmap myBitmap = BitmapFactory.decodeStream(inputStream,null,options);
//                        Drawable img = new BitmapDrawable(c.getResources(), myBitmap); //cách này là đổi sang drawable
                        Drawable img = Drawable.createFromPath(imgFile.getAbsolutePath());
                        String subArtist = q.getString(artist);
                        albums = new Albums(q.getString(album), subArtist, img);
                    } else {
                        //cách này đổi sang bitmap.
                        //thay hình chỗ này.

//                        Bitmap myBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.icon_player_black);
                            Drawable img = c.getResources().getDrawable(R.drawable.icon_player1); //cách này là đổi sang drawable
                        String subArtist = q.getString(artist);
                        albums = new Albums(q.getString(album), subArtist, img);

                    }
                    arrayListAlbum.add(albums);

                } while (q.moveToNext());
            }
//
        } catch (Exception e) {
            Log.d("LOI", "ScanAllAlbum: " + e.getMessage());
        }
    }

    //    public void ScanAllAlbumArt(Context c){
//        try {
//            ContentResolver contentResolver = c.getContentResolver();
//            String[] projection = {
//                    MediaStore.Audio.Albums._ID,
//                    MediaStore.Audio.Albums.ALBUM_ART};
//            String sortOrder = MediaStore.Audio.Albums._ID + " ASC";
////                String where = MediaStore.Audio.Media.TITLE + " LIKE ?";
////                String[] params = new String[] { "%life%" };
//            Cursor q = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
//                    projection, null, null, sortOrder);
//            if (q.moveToFirst()) {
//                int art = q.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
//                do {
//                    String coverPath = q.getString(art);
//                    if (coverPath != null) {
////                            Drawable img = Drawable.createFromPath(coverPath); cách này là đổi sang drawable
//                        //cách này đổi sang bitmap.
//                        File imgFile = new  File(coverPath);
//                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                        //Drawable d = new BitmapDrawable(getResources(), myBitmap);cách này là đổi sang drawable
//
//                        arrayListAlbumArt.add(myBitmap);
//                    }else{
//                        //cách này đổi sang bitmap.
//                        Bitmap myBitmap = BitmapFactory.decodeResource(c.getResources(),R.drawable.itunes_256);
////                            Drawable img = c.getResources().getDrawable(R.drawable.music_48); cách này là đổi sang drawable
//                        arrayListAlbumArt.add(myBitmap);
//                    }
//                } while (q.moveToNext());
//            }
////
//        } catch (Exception e) {
//            Log.d("LOI", "ScanAllAlbum: " + e.getMessage());
//        }
//    }
    public void ScanAllArtist(Context c) {
        try {
            ContentResolver contentResolver = c.getContentResolver();
            String[] projection = {
                    MediaStore.Audio.Artists.ARTIST,
                    MediaStore.Audio.Artists.ARTIST_KEY};
            String sortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";

//                String where = MediaStore.Audio.Media.TITLE + " LIKE ?";
//                String[] params = new String[] { "%life%" };
            Cursor q = contentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                    projection, null, null, sortOrder);
            if (q.moveToFirst()) {
                int artist = q.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
//                int position = 0;
                countArtist = q.getCount();
                do {
                    String subArtist = q.getString(artist);
                    Artists s = new Artists(subArtist);
                    arrayListArtist.add(s);

                } while (q.moveToNext());
            }
//
        } catch (Exception e) {
            Log.d("LOI", "ScanAllArtist: " + e.getMessage());
        }
    }


//
//    final String MEDIA_PATH = new String("/sdcard/");
//
//
//    public FileSong() {
//    }
//
//    public ArrayList<HashMap<String, String>> getPlayList() {
//        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
//        File home = new File(MEDIA_PATH);
//        if (home.listFiles(new FileExtensionFilter()).length > 0){
//            for (File file : home.listFiles(new FileExtensionFilter())){
//                HashMap<String, String> song = new HashMap<String, String>();
//                song.put("songTitle", file.getName().substring(0,(file.getName().length() - 4)));
//                song.put("songPath", file.getPath());
//
//                songsList.add(song);
//            }
//        }
//        return songsList;
//    }
//    class FileExtensionFilter implements FilenameFilter{
//
//        @Override
//        public boolean accept(File dir, String name) {
//            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
//        }
//    }
}
