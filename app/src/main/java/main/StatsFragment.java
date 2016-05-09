package main;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mckenna.musicdiscovery.R;

import db.DatabaseHandler;


public class StatsFragment extends Fragment {

    public ListView listView;
    public ImageView imageView;
    public View rootView;

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
        rootView = inflater.inflate(R.layout.fragment_stats, container, false);

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

        // Most played artist
        TextView mpArtist = (TextView)rootView.findViewById(R.id.mpArtist);
        TextView artistCardTitle = (TextView)rootView.findViewById(R.id.artistCardTitle);

        Cursor artistCursor = db.getMostPlayedArtist();

        artistCursor.moveToFirst();
        String mpArtistS = artistCursor.getString(artistCursor.getColumnIndexOrThrow("artist"));
        mpArtist.setText(mpArtistS);
        artistCardTitle.setText("Your most played artist is ");

        // Most played song
        TextView songCardTitle = (TextView)rootView.findViewById(R.id.songCardTitle);
        TextView songCardArtist = (TextView)rootView.findViewById(R.id.songCardArtist);
        TextView songCardAlbum = (TextView)rootView.findViewById(R.id.songCardAlbum);
        TextView songCardCount = (TextView)rootView.findViewById(R.id.songCardCount);
        ImageView songCardCover = (ImageView)rootView.findViewById(R.id.songCardCover);

        Cursor songCursor = db.getMostPlayedSong();
        songCursor.moveToFirst();

        String mpSongArtist = songCursor.getString(songCursor.getColumnIndexOrThrow("artist"));
        String mpSongS = songCursor.getString(songCursor.getColumnIndexOrThrow("title"));
        String mpSongAlbum = songCursor.getString(songCursor.getColumnIndexOrThrow("album"));
        String mpSongCount = songCursor.getString(songCursor.getColumnIndexOrThrow("count"));
        String mpSongCover = songCursor.getString(songCursor.getColumnIndexOrThrow("cover"));

        Uri coverUri = Uri.parse(mpSongCover);

        songCardTitle.setText(mpSongS);
        songCardArtist.setText(mpSongArtist);
        songCardAlbum.setText(mpSongAlbum);
        songCardCount.setText(mpSongCount);
        songCardCover.setImageURI(coverUri);
    }
}
