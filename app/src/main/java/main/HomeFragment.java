package main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mckenna.musicdiscovery.R;

import adapter.SongCursorAdapter;
import db.DatabaseHandler;

/**
 * Created by mckenna on 5/4/16.
 */
public class HomeFragment extends Fragment{
    private Context ctx;
    public ListView lv;
    public ImageView imageView;
    public HomeFragment() {
        // Required empty public constructor
        this.ctx = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        lv = (ListView)rootView.findViewById(R.id.listView);
        imageView = (ImageView)rootView.findViewById(R.id.cover);

        displayListView();
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

    public void displayListView(){
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        Cursor cursor = db.getAllRows();

        SongCursorAdapter songAdapter = new SongCursorAdapter(getContext(), cursor, 0);

        lv.setAdapter(songAdapter);
    }
}
