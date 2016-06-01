package main;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import android.support.v4.widget.DrawerLayout;

import com.example.mckenna.musicdiscovery.FragmentDrawer;
import com.example.mckenna.musicdiscovery.R;

import java.util.List;

import adapter.SongCursorAdapter;
import db.DatabaseHandler;
import db.Song;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    FragmentDrawer drawerFrag;
    Toolbar mToolbar;
    private EditText searchText;

    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 1;

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

        searchText = (EditText) this.findViewById(R.id.search_box);
        ListView lv = (ListView) findViewById(R.id.listView);
        searchText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                ListView lv = (ListView) findViewById(R.id.listView);
                SongCursorAdapter filterAdapter = (SongCursorAdapter) lv.getAdapter();
                filterAdapter.getFilter().filter(s.toString());
            }
        });

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

        displayView(0);

        //If we're running on a device above android version 6, we have to request permissions on runtime.
        if(android.os.Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "External read permission has been granted.", Toast.LENGTH_SHORT).show();
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "External storage permission required to access music files", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_RESULT);
            }
        }

        new ServletPostAsyncTask().execute(new Pair<Context, String>(this, "Music bang"));

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

        if(id == R.id.action_search){
            HomeFragment tempFrag = (HomeFragment)getSupportFragmentManager().findFragmentByTag(getString(R.string.title_home));
            //Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            if(searchText.getVisibility() == View.VISIBLE) {
                searchText.setVisibility(View.GONE);
                tempFrag.displayListView();
            }
            else{
                searchText.setVisibility(View.VISIBLE);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            String albumString = null;

            /* To get album art */
            long songId = intent.getLongExtra("id", -1);
            if(songId != 1) {
                String selection = MediaStore.Audio.Media._ID + " = " + songId + " ";

                Cursor cursor = getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[] {
                                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM_ID},
                        selection, null, null);

                if (cursor.moveToFirst()) {
                    long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Log.d("Album ID : ", ""+albumId);

                    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);

                    //set the album art in imageview
                    //albumArt.setImageURI(albumArtUri);
                    albumString = albumArtUri.toString();
                }
                cursor.close();
            }
            else{
                albumString = null;
            }

            Song song = new Song(track, artist, album, albumString);
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
            HomeFragment tempFrag = (HomeFragment)getSupportFragmentManager().findFragmentByTag(getString(R.string.title_home));
            tempFrag.displayListView();
        }
    };

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new StatsFragment();
                title = getString(R.string.title_statistics);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
