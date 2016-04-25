package com.example.mckenna.musicdiscovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

/**
 * Created by mckenna on 4/24/16.
 */
public class Feed extends ListFragment{
    int[] logos = new int[]{
            R.drawable.blue,
            R.drawable.green,
            R.drawable.orange,
            R.drawable.pink
    };

    List<Song> songs = new ArrayList<Song>(Arrays.asList(new Song[]{
            new Song("Little Games", "The Colourist", "idk"),
            new Song("Thanks", "Blah Blah", "Yup")
    }));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupList();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setupList() {
        List<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("title", song.getTitle());
            hm.put("subtitle", song.getArtist());
            hm.put("logo", Integer.toString(logos[i % logos.length]));
            rows.add(hm);
        }
        String[] from = {"logo", "title", "subtitle"};
        int[] to = {R.id.logo, R.id.title, R.id.subtitle};

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), rows, R.layout.listview_layout, from, to);
        setListAdapter(adapter);
    }

    public void addSong(Song song) {
        songs.add(song);
        setupList();
    }
}
