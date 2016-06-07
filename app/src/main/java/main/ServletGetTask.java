package main;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Servlet post async task to execute backend functions
 */

class ServletGetTask extends AsyncTask<Context, Void, String> {
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        context = params[0];

        try {
            // Set up the request
            URL url = new URL("https://music-discovery-1316.appspot.com/storedata");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            String getParam = "artist";

            // Execute HTTP Post
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getParam);
            writer.flush();
            writer.close();
            outputStream.close();
            connection.connect();

            // Read response
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                return response.toString();
            }
            return "Error: " + responseCode + " " + connection.getResponseMessage();

        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String buildPostDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
