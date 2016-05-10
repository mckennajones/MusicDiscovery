package main;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.Map;

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
        SQLiteDatabase database = db.getReadableDatabase();
        // Most played artist
        TextView mpArtist = (TextView)rootView.findViewById(R.id.mpArtist);
        TextView artistCardTitle = (TextView)rootView.findViewById(R.id.artistCardTitle);

        Cursor artistCursor = db.getMostPlayedArtist();

        artistCursor.moveToFirst();
        String mpArtistS = artistCursor.getString(artistCursor.getColumnIndexOrThrow("artist"));
        mpArtist.setText(mpArtistS);
        artistCardTitle.setText("You've listened to the most songs by ");

        // Most played song
        TextView songCardTitle = (TextView)rootView.findViewById(R.id.songCardTitle);
        TextView songCardSong = (TextView)rootView.findViewById(R.id.songCardSong);
        TextView songCardArtist = (TextView)rootView.findViewById(R.id.songCardArtist);
        TextView songCardCount = (TextView)rootView.findViewById(R.id.songCardCount);

        Cursor songCursor = db.getMostPlayedSong();
        songCursor.moveToFirst();

        String mpSongArtist = songCursor.getString(songCursor.getColumnIndexOrThrow("artist"));
        String mpSongS = songCursor.getString(songCursor.getColumnIndexOrThrow("title"));
        String mpSongCount = songCursor.getString(songCursor.getColumnIndexOrThrow("count"));

        songCardTitle.setText("Your most played song is ");
        songCardSong.setText(mpSongS);
        songCardArtist.setText(mpSongArtist);
        songCardCount.setText("Played " + mpSongCount + " times");

        // Get most played artist w/ count
        TextView artistCard2Title = (TextView)rootView.findViewById(R.id.artistCard2Title);
        TextView artistCard2Artist = (TextView)rootView.findViewById(R.id.artistCard2Artist);

        String mpArtist2 = "No artists played";
        Integer mpArtistCnt = -2;
        Map<String,Integer> artistMap = db.getArtistStats();
        for(Map.Entry<String, Integer> entry: artistMap.entrySet()){
            if(entry.getValue() > mpArtistCnt){
                mpArtistCnt = entry.getValue();
                mpArtist2 = entry.getKey();
            }
        }

        artistCard2Title.setText("You've spent the most time listening to");
        artistCard2Artist.setText(mpArtist2);

        // Get most played album w/ count
        TextView albumCardTitle = (TextView)rootView.findViewById(R.id.albumCardTitle);
        TextView albumCardAlbum = (TextView)rootView.findViewById(R.id.albumCardAlbum);

        String mpAlbum2 = "No albums played";
        Integer mpAlbumCnt = -2;
        Map<String,Integer> albumMap = db.getAlbumStats();
        for(Map.Entry<String, Integer> entry: albumMap.entrySet()){
            if(entry.getValue() > mpAlbumCnt){
                mpAlbumCnt = entry.getValue();
                mpAlbum2 = entry.getKey();
            }
        }
        String coverQuery = "SELECT cover FROM songs WHERE album = '" + mpAlbum2 + "'";
        Cursor albumCursor = database.rawQuery(coverQuery, null);
        albumCursor.moveToFirst();
        String cover = albumCursor.getString(albumCursor.getColumnIndexOrThrow("cover"));
        Uri coverUri = Uri.parse(cover);
        ImageView coverView = (ImageView)rootView.findViewById(R.id.albumCardCover);
        albumCardTitle.setText("You've spent the most time listening to");
        albumCardAlbum.setText(mpAlbum2);
        coverView.setImageURI(coverUri);
    }
}
