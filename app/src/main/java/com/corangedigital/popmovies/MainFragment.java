package com.corangedigital.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
// iTMDB is an interface that provide a call back function for the asyncTask
public class MainFragment extends Fragment implements iTMDB {

    public MovieListAdapter mMovieAdapter;
    public ArrayList<TMDBMovie> movieList;
    private final String LOG_TAG = MainFragment.class.getSimpleName();
    public final String BaseUrl ="http://image.tmdb.org/t/p/w185";

    @Override
    public void onArrayListFilled(ArrayList<TMDBMovie> movieArrayList) {

        GridView gridView = (GridView) getView().findViewById(R.id.gridview_movies);
        getView().findViewById(R.id.gridview_movies).setVisibility(View.VISIBLE);


        if (movieList == null) {
            movieList = new ArrayList<TMDBMovie>();
        }
        movieList = movieArrayList;
        mMovieAdapter = new MovieListAdapter(getActivity(), R.layout.list_item_movie, movieArrayList);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra("movie", movieList.get(position));
                getActivity().startActivity(intent);
            }
        });

//        gridView.invalidate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movielist", movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (movieList == null) {movieList = new ArrayList<TMDBMovie>();}
            movieList = savedInstanceState.getParcelableArrayList("movielist");}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        // If there is no saved instance, we set the gridview visibility to gone, until data are ready to be displayed
        if (savedInstanceState == null) {
            rootView.findViewById(R.id.gridview_movies).setVisibility(View.GONE);
            FetchTMDB movieTask = new FetchTMDB(this, getString(R.string.api_key));
            movieTask.execute(getString(R.string.sort_option_pop));
        }
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_sort_pop) {
            FetchTMDB movieTask = new FetchTMDB(this, getString(R.string.api_key));
            movieTask.execute(getString(R.string.sort_option_pop));
            return true;
        }
        if (id == R.id.action_sort_rate) {
            FetchTMDB movieTask = new FetchTMDB(this, getString(R.string.api_key));
            movieTask.execute(getString(R.string.sort_option_rat));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}