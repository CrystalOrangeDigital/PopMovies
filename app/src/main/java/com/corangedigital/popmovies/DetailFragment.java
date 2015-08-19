package com.corangedigital.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    private static final String MOVIE_SHARE_HASHTAG = " #popularmovieapp";
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private String mMovie;


    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        rootView.setVerticalScrollBarEnabled(true);
        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra("movie")) {
            // 1 - Getting the movie object from the intent
            TMDBMovie movieDetail = intent.getParcelableExtra("movie");

            // 2 - Retrieving views from the detail fragment
            TextView titleView = (TextView) rootView.findViewById(R.id.textview_title);
            ImageView posterView = (ImageView) rootView.findViewById(R.id.imageview_poster_detail_fragment);
            TextView dateView = (TextView) rootView.findViewById(R.id.textview_release_date);
            TextView ratingView = (TextView) rootView.findViewById(R.id.textview_rating);
            TextView overviewView = (TextView) rootView.findViewById(R.id.textview_overview);

            // 3 - Setting the UI Parameter
            titleView.setText(movieDetail.getTitle());
            Picasso.with(rootView.getContext()).load(movieDetail.getThumbnailPath()).into(posterView);
            dateView.setText(movieDetail.getReleaseDate());
            ratingView.setText(movieDetail.getVoteAverage());
            overviewView.setText(movieDetail.getOverview());

        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_fragment, menu);
    }

}
