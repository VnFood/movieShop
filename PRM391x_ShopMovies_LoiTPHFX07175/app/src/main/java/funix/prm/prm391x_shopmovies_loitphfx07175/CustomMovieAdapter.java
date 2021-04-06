package funix.prm.prm391x_shopmovies_loitphfx07175;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomMovieAdapter extends BaseAdapter {
    // variables
    private final LayoutInflater layoutInflater;
    private final List<Movie> listMovie;
    private final Context applicationContext;

    /**
     * Movie adapter constructor
     * @param applicationContext Context
     * @param listMovie List movie
     */
    public CustomMovieAdapter(Context applicationContext, List<Movie> listMovie) {
        layoutInflater = (LayoutInflater.from(applicationContext));
        this.listMovie = listMovie;
        this.applicationContext = applicationContext;
    }

    /**
     * Calculate size movie list
     * @return size movie list
     */
    @Override
    public int getCount() { return listMovie.size(); }

    /**
     * Get item movie
     * @param i index
     * @return item movie
     */
    @Override
    public Object getItem(int i) { return listMovie.get(i); }

    /**
     * Get id item movie
     * @param i index
     * @return id item movie
     */
    @Override
    public long getItemId(int i) { return 0; }

    /**
     * Custom view movie list
     * @param i index
     * @param view view
     * @param viewGroup viewGroup
     * @return view movie list
     */
    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.movie_item, null);

        // get elements
        ImageView itemMoviePicture = view.findViewById(R.id.itemMoviePicture);
        TextView itemMovieTitle = view.findViewById(R.id.itemMovieTitle);
        TextView itemMoviePrice = view.findViewById(R.id.itemMoviePrice);

        // set data into elements
        Picasso.with(this.applicationContext)
                .load(listMovie.get(i).getMoviePicture())
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemMoviePicture);
        itemMovieTitle.setText(listMovie.get(i).getMovieTitle());
        itemMoviePrice.setText(listMovie.get(i).getMoviePrice());

        return view;
    }
}
