package com.example.mckenna.musicdiscovery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import java.util.List;

/**
 * Class to retrieve music metadata
 */
public class MediaPlayer extends Activity {

    public static final String SERVICECMD = "com.android.music.musicservicecommand";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_player);

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");
        iF.addAction("com.android.music.metachanged");

        iF.addAction("com.htc.music.metachanged");

        iF.addAction("fm.last.android.metachanged");
        iF.addAction("com.sec.android.app.music.metachanged");
        iF.addAction("com.nullsoft.winamp.metachanged");
        iF.addAction("com.amazon.mp3.metachanged");
        iF.addAction("com.miui.player.metachanged");
        iF.addAction("com.real.IMP.metachanged");
        iF.addAction("com.sonyericsson.music.metachanged");
        iF.addAction("com.rdio.android.metachanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        iF.addAction("com.andrew.apollo.metachanged");

        registerReceiver(mReceiver, iF);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
            Log.v("tag ", action + " / " + cmd);
            Log.d("Insert: ", "Inserting ..");
            String artist = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            String track = intent.getStringExtra("track");
            Song song = new Song(track, artist, album);
            db.addSong(song);
            Log.v("tag", artist + ":" + album + ":" + track);
            //Toast.makeText(MediaPlayer.this, track, Toast.LENGTH_SHORT).show();
            //Toast.makeText(MediaPlayer.this, artist, Toast.LENGTH_SHORT).show();
//            List<Song> songs = db.getSongs();
//            for (Song sn : songs) {
//                String log = "Id: " + song.getId() + " ,Name: " + song.getTitle() + " ,Phone: " + song.getArtist();
//                // Writing Contacts to log
//                Log.d("Name: ", log);
//            }

        }
    };


}
