package com.example.mckenna.musicdiscovery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite database to keep track of songs
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
    public void addSong(Song song){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, song.getTitle());
        values.put(KEY_ARTIST, song.getArtist());
        values.put(KEY_ALBUM, song.getAlbum());

        db.insert(TABLE_SONGS, null, values);
        db.close();
    }

    public Song getSong(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        // consider moving string out of query here for readability
        Cursor cursor = db.query(TABLE_SONGS, new String[] {KEY_SONGID, KEY_TITLE, KEY_ARTIST, KEY_ALBUM}, KEY_SONGID + "=?", new String[] { String.valueOf(id)}, null, null, null, null);

        Song song = new Song(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));

        cursor.close();
        return song;
    }

    public List<Song> getSongs(){
        List<Song> songList = new ArrayList<Song>();

        String selectAll = "SELECT * FROM " + TABLE_SONGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectAll, null);

        if(cursor.moveToFirst()){
            do{
                Song song = new Song();
                song.setId(Integer.parseInt(cursor.getString(0)));
                song.setTitle(cursor.getString(1));
                song.setArtist(cursor.getString(2));
                song.setAlbum(cursor.getString(3));

                songList.add(song);
            }while(cursor.moveToNext());
        }

        cursor.close();
        return songList;
    }

    public int getSongCount(){
        String getCount = "SELECT * FROM " + TABLE_SONGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getCount, null);
        cursor.close();

        return cursor.getCount();
    }

    /*
    probably don't need this one?
    public int updateSong(Song song){}
    */

    public void deleteSong(Song song){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SONGS, KEY_SONGID + " = ?", new String[] { String.valueOf(song.getId())});
        db.close();
    }
}
