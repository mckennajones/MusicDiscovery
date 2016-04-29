package com.example.mckenna.musicdiscovery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;

import java.util.List;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            //Toast.makeText(MainActivity.this, track, Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, artist, Toast.LENGTH_SHORT).show();
//            List<Song> songs = db.getSongs();
//            for (Song sn : songs) {
//                String log = "Id: " + song.getId() + " ,Name: " + song.getTitle() + " ,Phone: " + song.getArtist();
//                // Writing Contacts to log
//                Log.d("Name: ", log);
//            }

        }
    };

    public void musicIntent(View v)
    {
        Intent musicPlayer = new Intent(MainActivity.this, MediaPlayer.class);
        MainActivity.this.startActivity(musicPlayer);
    }

    //final static int ADD_ITEM_INTENT = 1; // use to signify result of adding item

    /*protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_ITEM_INTENT:
                    String title = returnIntent.getStringExtra("title");
                    String artist = returnIntent.getStringExtra("artist");
                    String album = returnIntent.getStringExtra("album");
                    if (title != null && artist != null) {
                        Toast.makeText(this, title + " (" + artist + ")", Toast.LENGTH_SHORT).show();
                        Feed feedList = (Feed)
                                getSupportFragmentManager().findFragmentById(R.id.feed_fragment);
                        feedList.addSong(new Song(title, artist, album));
                    }
                    break;
                // could handle other intent callbacks here, too
            }
        }
    }*/
}
