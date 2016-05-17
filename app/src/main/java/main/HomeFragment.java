package main;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mckenna.musicdiscovery.R;

import adapter.SongCursorAdapter;
import db.DatabaseHandler;

/**
 * Fragment that is shown when app first starts
 */
public class HomeFragment extends Fragment {
    public ListView lv;
    public ImageView imageView;
    View rootView;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        lv = (ListView) rootView.findViewById(R.id.listView);
        imageView = (ImageView) rootView.findViewById(R.id.cover);

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

    public void displayListView() {
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        Cursor cursor = db.getAllRows();

        SongCursorAdapter songAdapter = new SongCursorAdapter(getContext(), cursor, 0);

        lv.setAdapter(songAdapter);

        songAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return getDirectoryList(constraint);
            }
        });
    }

    public Cursor getDirectoryList(CharSequence constraint) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        DatabaseHandler db = new DatabaseHandler(getContext().getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();

        queryBuilder.setTables("songs");

        if(constraint==null||constraint.length()==0)
        {
            //  Return the full list
            return queryBuilder.query(database, null, null, null,
                    null, null, "count");
        }

        else
        {
            String value = "'" + constraint.toString() + "'";

            return database.query("songs", null, "title = " + value + " OR artist = " + value + " OR album = " + value, null, null, null, null);
        }
    }

}


