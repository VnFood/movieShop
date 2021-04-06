package funix.prm.prm391x_shopmovies_loitphfx07175;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends Activity {
    // variables
    static public List<Movie> movieList = new ArrayList<>();
    private final String TAG = MovieActivity.class.getSimpleName();
    public List<Movie> fetchMovieListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_main);

        // init data movie
        new GetMovieData().execute();
    }

    /**
     * Call Movie activity
     * @param view view
     */
    public void openMovieActivity(View view) {
        Intent intent = new Intent(MovieActivity.this, MovieActivity.class);
        startActivity(intent);
    }

    /**
     * Call Profile activity
     * @param view view
     */
    public void openProfileActivity(View view) {
        Intent intent = new Intent(MovieActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    /**
     * Get movie list
     * @return movie list
     */
    public List<Movie> getMovieList() {
        return movieList;
    }

    /**
     * Set movie list
     * @param movieList movie list
     */
    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    /**
     * Set private GetMovieData class
     */
    private class GetMovieData extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // start get data
        }

        /**
         * Handle active get data
         * @param params params
         * @return void
         */
        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url ="https://api.androidhive.info/json/movies_2017.json";
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray movie = new JSONArray(jsonStr);
                    // looping through all movies
                    for (int i = 0; i < movie.length(); i++) {
//                        String image = movie.getJSONObject(i).getString("image");
//                        String title = movie.getJSONObject(i).getString("title");
//                        String price = movie.getJSONObject(i).getString("price");

                        int image = R.drawable.movie_sample;
                        String title = "Movie Title Sample";
                        String price = movie.getJSONObject(i).getString("price");

                        fetchMovieListData.add( new Movie(image, title, price));
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            for (int i = 0; i <= 100; i+=10) {
                SystemClock.sleep(100);
                // when this function has called, onProgressUpdate execute
                publishProgress(i);
            }

            return null;
        }

        /**
         * Update UI with data
         * @param values values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            // update UI when receive data from doInBackground
            super.onProgressUpdate(values);
            setMovieList(fetchMovieListData);

            GridView gridMovie = findViewById(R.id.gridMovie);
            CustomMovieAdapter customMovieAdapter = new CustomMovieAdapter(
                    getApplicationContext(),
                    getMovieList()
            );
            gridMovie.setAdapter(customMovieAdapter);
        }

        /**
         * Call execute
         * @param aVoid void
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
