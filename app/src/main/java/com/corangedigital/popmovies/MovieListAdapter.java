package com.corangedigital.popmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ebal on 28/07/15.
 */
public class MovieListAdapter extends ArrayAdapter<TMDBMovie> {
    private MainFragment mainFragment;
    TMDBMovie[] movieListTab;

    public MovieListAdapter(Context context, int layoutResourceId, ArrayList<TMDBMovie> movies) {
        super(context, layoutResourceId, movies);
        movieListTab = movies.toArray(new TMDBMovie[movies.size()]);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (imageView == null) {
            imageView = (ImageView) inflater.inflate(R.layout.list_item_movie, parent, false);
        }
        String imageUrl = new String();
        imageUrl = movieListTab[position].getThumbnailPath();
        if (imageView != null) {
            Picasso.with(getContext()).load(imageUrl).into(imageView);
//            imageView.setOnClickListener(new OnClickListener() {
//                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(), DetailActivity.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .putExtra("movie", movieListTab[position]);
//                    getContext().startActivity(intent);
//                }
//            });


        }
        return imageView;
    }
}
