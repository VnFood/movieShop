package funix.prm.prm391x_shopmovies_loitphfx07175;

public class Movie {
    // variables
    private final int moviePicture;
    private final String movieTitle;
    private final String moviePrice;

    /**
     * Movie constructor
     * @param moviePicture movie picture
     * @param movieTitle movie title
     * @param moviePrice movie price
     */
    public Movie(int moviePicture, String movieTitle, String moviePrice) {
        super();
        this.moviePicture = moviePicture;
        this.movieTitle = movieTitle;
        this.moviePrice = moviePrice;
    }

    /**
     * Set movie picture
     * @return movie picture
     */
    public int getMoviePicture() {
        return moviePicture;
    }

    /**
     * Set movie title
     * @return movie title
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Set movie price
     * @return movie price
     */
    public String getMoviePrice() {
        return moviePrice;
    }

}
