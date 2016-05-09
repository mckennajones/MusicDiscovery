package db;

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
    public static final String TABLE_SONGS = "songs";

    // Column Name(s)
    public static final String KEY_SONGID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_ALBUM = "album";
    public static final String KEY_COUNT = "count";
    public static final String KEY_COVER = "cover";
    private static final String[] ALL_KEYS = new String[]{KEY_SONGID, KEY_TITLE, KEY_ARTIST, KEY_ALBUM, KEY_COUNT, KEY_COVER};
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables here
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_SONGS_TABLE = "CREATE TABLE " + TABLE_SONGS + "("
                + KEY_SONGID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
                + KEY_ARTIST + " TEXT," + KEY_ALBUM + " TEXT," + KEY_COUNT + " INTEGER," + KEY_COVER + " TEXT" + ")";
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
        String query = "SELECT * FROM " + TABLE_SONGS + " WHERE " + KEY_TITLE + " = '" + checkTitle(song.getTitle()) + "'";
        Cursor cursor = db.rawQuery(query, null);
        ContentValues values = new ContentValues();

        if(cursor.getCount() > 0) {
            query = "SELECT " + KEY_COUNT + " FROM " + TABLE_SONGS + " WHERE " + KEY_TITLE + " = '" + checkTitle(song.getTitle()) + "'";
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            String count = cursor.getString(cursor.getColumnIndex(KEY_COUNT));
            int cnt = Integer.parseInt(count);
            values.put(KEY_TITLE, song.getTitle());
            values.put(KEY_ARTIST, song.getArtist());
            values.put(KEY_ALBUM, song.getAlbum());
            values.put(KEY_COUNT, cnt + 1);
            values.put(KEY_COVER, song.getAlbumCover());

            String whereClause = KEY_TITLE + " = '" + song.getTitle() + "'";
            db.update(TABLE_SONGS, values, whereClause, null);
        }
        else {
            values.put(KEY_TITLE, song.getTitle());
            values.put(KEY_ARTIST, song.getArtist());
            values.put(KEY_ALBUM, song.getAlbum());
            values.put(KEY_COUNT, 1);
            values.put(KEY_COVER, song.getAlbumCover());
            db.insert(TABLE_SONGS, null, values);
        }
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

    public Cursor getAllRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        String where = null;
        Cursor c = 	db.query(true, TABLE_SONGS, ALL_KEYS,
                where, null, null, null, KEY_COUNT + " DESC", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getMostPlayedSong(){
        SQLiteDatabase db = this.getReadableDatabase();
        String highCount = "SELECT * FROM songs ORDER BY count DESC LIMIT 1";
        Cursor c = db.rawQuery(highCount, null);

        return c;
    }

    public Cursor getMostPlayedArtist(){
        SQLiteDatabase db = this.getReadableDatabase();
        String artist = "SELECT _id, artist, COUNT(artist) as artist_count FROM songs GROUP BY artist ORDER BY artist_count DESC LIMIT 1";

        Cursor c = db.rawQuery(artist, null);

        return c;
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

    public String checkTitle(String title){
        int i = 0;
        String fixedTitle = title;
        for(i=0;i<title.length();i++) {
            if(title.charAt(i) == '\''){
                fixedTitle = title.substring(0,i) + "\'" + title.substring(i,title.length()-1);
            }
        }
        return fixedTitle;

    }
}
