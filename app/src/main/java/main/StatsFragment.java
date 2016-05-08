package main;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.CardView;

import com.example.mckenna.musicdiscovery.R;

import java.util.List;

import adapter.MostPlayedSong;
import adapter.SongCursorAdapter;
import db.DatabaseHandler;


public class StatsFragment extends Fragment {

    public ListView listView;
    public ImageView imageView;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        listView = (ListView)rootView.findViewById(R.id.statsList);

        displayStats();
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void displayStats(){
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        Cursor cursor = db.getMostPlayedSong();

        MostPlayedSong songAdapter = new MostPlayedSong(getContext(), cursor, 0);

        listView.setAdapter(songAdapter);
    }
}
