package com.corangedigital.popmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ebal on 18/08/15.
 */

public class FetchTMDB extends AsyncTask<String, Void, ArrayList<TMDBMovie>> {

    public static ArrayList<TMDBMovie> movieList;
    private String apiKey;
    private iTMDB listener;

    public FetchTMDB(iTMDB listener, String apiKey) {
        this.listener = listener;
        this.apiKey = apiKey;
    }

    private ArrayList<TMDBMovie> getMovieDataFromJson(String movieJsonStr) throws JSONException {

        // Field names in JSON
        final String TMDB_RESULTS = "results";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_TITLE = "title";
        final String TMDB_RELEASE_DATE = "release_date";
        final String TMDB_VOTE_AVERAGE = "vote_average";
        final String TMDB_PLOT_OVERVIEW = "overview";


        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieList = movieJson.getJSONArray(TMDB_RESULTS);

        // For each row in the incoming data, we create a TMDB object
        // Then we add it to the list Array that is passed back to Fragment
        TMDBMovie movie;
        ArrayList<TMDBMovie> movieArrayList = new ArrayList<TMDBMovie>();
        String[] movieInfo = new String[5];

        for (int i = 0; i < movieList.length(); i++) {
            JSONObject resultItem = movieList.getJSONObject(i);
            movieInfo[0] = resultItem.getString(TMDB_TITLE);
            movieInfo[1] = resultItem.getString(TMDB_POSTER_PATH);
            movieInfo[2] = resultItem.getString(TMDB_RELEASE_DATE);
            movieInfo[3] =resultItem.getString(TMDB_VOTE_AVERAGE);
            movieInfo[4] = resultItem.getString(TMDB_PLOT_OVERVIEW);

            movie = new TMDBMovie(movieInfo);
            movieArrayList.add(movie);

        }
        return movieArrayList;

    }

    @Override
    protected void onPostExecute(ArrayList<TMDBMovie> tmdbMovies) {
        super.onPostExecute(tmdbMovies);
        listener.onArrayListFilled(tmdbMovies);
    }

    @Override
    protected ArrayList<TMDBMovie> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieJsonStr = null;

        // Selecting between highest rated and Most popular criteria
        String sortOption = params[0];
//        String apiKey = params[1];

        try {
                /* URI Builder Code */
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, sortOption)
                    .appendQueryParameter(API_KEY_PARAM, apiKey)
                    .build();

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            movieJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("PopMovieFragment", "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PopMovieFragment", "Error closing stream", e);
                }
            }
        }
        try {
            return getMovieDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e("PopMovieFragment", e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}
