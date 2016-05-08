package adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mckenna.musicdiscovery.R;

import java.net.ConnectException;

/**
 * Created by mckenna on 5/4/16.
 */
public class MostPlayedSong extends CursorAdapter {

    public MostPlayedSong(Context context, Cursor cursor, int flags){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.stats_card, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = (TextView)view.findViewById(R.id.cardTitle);
        TextView tvArtist = (TextView)view.findViewById(R.id.cardArtist);
        TextView tvAlbum = (TextView)view.findViewById(R.id.cardAlbum);
        TextView tvCount = (TextView)view.findViewById(R.id.cardCount);

        ImageView coverView = (ImageView)view.findViewById(R.id.cardCover);

        String artist = cursor.getString(cursor.getColumnIndexOrThrow("artist"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String album = cursor.getString(cursor.getColumnIndexOrThrow("album"));
        String count = cursor.getString(cursor.getColumnIndexOrThrow("count"));
        String cover = cursor.getString(cursor.getColumnIndexOrThrow("cover"));

        Uri coverUri = Uri.parse(cover);

        coverView.setImageURI(coverUri);
        tvTitle.setText(title);
        tvArtist.setText(artist);
        tvAlbum.setText(album);
        tvCount.setText(count);
    }
}
