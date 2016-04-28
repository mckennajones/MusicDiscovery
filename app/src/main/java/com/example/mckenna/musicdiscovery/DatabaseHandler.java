package com.example.mckenna.musicdiscovery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by mckenna on 4/28/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // Version
    private static final int DATABASE_VERSION = 1;

    // Databse Name
    private static final String DATABASE_NAME = "songManager";

    // Table name(s)
    private static final String TABLE_SONGS = "songs";

    // Column Name(s)
    private static final String KEY_SONGID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ARTIST = "artist";
    private static final String KEY_ALBUM = "album";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables here
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_SONGS_TABLE = "CREATE TABLE " + TABLE_SONGS + "("
                + KEY_SONGID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_ARTIST + " TEXT," + KEY_ALBUM + " TEXT" + ")";
        db.execSQL(CREATE_SONGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        // if old exists, drop it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);

        onCreate(db);
    }

    // CRUD
    public void addSong(Song song){}

    public Song getSong(int id){}

    public List<Song> getSongs(){}

    public int getSongCount(){}

    // probably don't need this one?
    public int updateSong(Song song){}

    public void deleteSong(Song song){}
}
