package com.example.mckenna.musicdiscovery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private FragmentDrawer drawerFrag;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        drawerFrag = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFrag.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFrag.setDrawerListener(this);
        /* Music intent stuff */
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

        //displayListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*private void displayListView(){
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        Cursor cursor = db.getAllRows();

        //startManagingCursor(cursor);

        String[] fromFieldNames = new String[]
                {db.KEY_TITLE, db.KEY_ARTIST, db.KEY_ALBUM, db.KEY_COUNT};
        int[] toViewIDs = new int[]
                {R.id.title,     R.id.artist,           R.id.album, R.id.count};

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.song_layout, cursor, fromFieldNames, toViewIDs);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);
    }*/

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
            if(artist != null && album != null && track != null) {
                db.addSong(song);
            }
            Log.v("tag", artist + ":" + album + ":" + track);
            //Toast.makeText(MainActivity.this, track, Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, artist, Toast.LENGTH_SHORT).show();
            List<Song> songs = db.getSongs();
            for (Song sn : songs) {
                String log = "Id: " + sn.getId() + " ,Name: " + sn.getTitle() + " ,Phone: " + sn.getArtist();
                // Writing Contacts to log
                Log.d("Name: ", log);
            }
            //displayListView();
        }
    };

    public void musicIntent(View v)
    {
        Intent musicPlayer = new Intent(MainActivity.this, MainActivity.class);
        MainActivity.this.startActivity(musicPlayer);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }
}
