package com.example.mckenna.musicdiscovery;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void musicIntent(View v)
    {
        Intent musicPlayer = new Intent(MainActivity.this, MediaPlayer.class);
        MainActivity.this.startActivity(musicPlayer);
    }

    final static int ADD_ITEM_INTENT = 1; // use to signify result of adding item

    protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
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
    }
}
