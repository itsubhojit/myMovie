package com.example.android.moviequeryapp.internet;

import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    //https://api.themoviedb.org/3/discover/movie?api_key=API_KEY&language=en-US&sort_by=popularity.desc&page=1
    private final static String DISCOVER_MOVIE_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private final static String POPULAR_MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    private final static String TOP_RATED_MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated";
    private final static String VIDEO_BASE_URL = "https://api.themoviedb.org/3/movie/";

    //private final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

    private final static String PARAM_QUERY_API = "api_key";
    private final static String PARAM_LANGUAGE = "language";
    private final static String ENGLISH = "en-US";

    private final static String PARAM_PAGE = "page";
    private final static String PARAM_SORT_BY = "sort_by";
    private final static String PARAM_INCLUDE_ADULT = "include_adult";
    private final static String PARAM_INCLUDE_VIDEO = "include_video";
    private static final int numPages = 1;

//    https://api.themoviedb.org/3/discover/movie?api_key=<<api_key>>


//    &language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1
    public static URL buildDiscoverMovieUrl(String passingApiKey) {
        Uri builtUri = Uri.parse(DISCOVER_MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY_API, passingApiKey)
                .appendQueryParameter(PARAM_LANGUAGE, "en-US")
                .appendQueryParameter(PARAM_SORT_BY, "popularity.desc")
                .appendQueryParameter(PARAM_INCLUDE_ADULT, "false")
                .appendQueryParameter(PARAM_INCLUDE_VIDEO, "false")
                .appendQueryParameter(PARAM_PAGE, Integer.toString(numPages))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

//    https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
    public static URL buildPopularMovieUrl(String passingApiKey) {
        Uri builtUri = Uri.parse(POPULAR_MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY_API, passingApiKey)
                .appendQueryParameter(PARAM_LANGUAGE, "en-US")
                .appendQueryParameter(PARAM_PAGE, Integer.toString(numPages))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    //  https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
    //  TOP RATED MOVIE URL
    public static URL buildTopRatedMovieUrl(String passingApiKey){
        Uri builtUri = Uri.parse(TOP_RATED_MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY_API, passingApiKey)
                .appendQueryParameter(PARAM_LANGUAGE, ENGLISH)
                .appendQueryParameter(PARAM_PAGE, Integer.toString(numPages))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }



    //  https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=084c79c7722ce9496963780c61fa46a1&language=en-US&page=1
    //  Trailer Video Url
    public static URL buildVideoUrl(int movie_id, String passingApiKey){
        String MOVIE_VIDEO_URL = VIDEO_BASE_URL + Integer.toString(movie_id) + "/videos";
        Uri builder = Uri.parse(MOVIE_VIDEO_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY_API,passingApiKey)
                .appendQueryParameter(PARAM_LANGUAGE, ENGLISH)
                .appendQueryParameter(PARAM_PAGE, Integer.toString(numPages))
                .build();

        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    ///////////////////   GETTING RESPONSE FROM HTTP SITE   /////////////////////
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();
        try {

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
