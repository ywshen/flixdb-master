package com.pushpal.popularmoviesstage1.networking;

import com.pushpal.popularmoviesstage1.model.MovieCreditResponse;
import com.pushpal.popularmoviesstage1.model.MovieLang;
import com.pushpal.popularmoviesstage1.model.MovieResponse;
import com.pushpal.popularmoviesstage1.model.MovieReviewResponse;
import com.pushpal.popularmoviesstage1.model.MovieTrailerResponse;
import com.pushpal.popularmoviesstage1.model.Person;
import com.pushpal.popularmoviesstage1.model.SearchResponse;
import com.pushpal.popularmoviesstage1.model.TvCreditResponse;
import com.pushpal.popularmoviesstage1.model.TvLang;
import com.pushpal.popularmoviesstage1.model.TvResponse;
import com.pushpal.popularmoviesstage1.model.TvReviewResponse;
import com.pushpal.popularmoviesstage1.model.TvVideoResponse;
import com.pushpal.popularmoviesstage1.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RESTClientInterface {
    @GET(Constants.SEARCH_URL)
    Call<SearchResponse> getMultiSearch(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET(Constants.POPULAR_MOVIES_URL)
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET(Constants.TOP_RATED_MOVIES_URL)
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET(Constants.UPCOMING_MOVIES_URL)
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET(Constants.NOW_PLAYING_MOVIES_URL)
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET(Constants.LATEST_MOVIES_URL)
    Call<MovieResponse> getLatestMovies(@Query("api_key") String apiKey);

    @GET(Constants.MOVIE_DETAILS_URL)
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET(Constants.LANGUAGES)
    Call<List<MovieLang>> getLanguages(@Query("api_key") String apiKey);

    @GET(Constants.LANGUAGES)
    Call<List<TvLang>> getTvLanguages(@Query("api_key") String apiKey);

    @GET(Constants.CREDITS_URL)
    Call<MovieCreditResponse> getCredits(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET(Constants.TV_CREDIT_URL)
    Call<TvCreditResponse> getTvCredits(@Path("tv_id") int tvId, @Query("api_key") String apiKey);

    @GET(Constants.TRAILERS_URL)
    Call<MovieTrailerResponse> getTrailers(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET(Constants.VIDEO_URL)
    Call<TvVideoResponse> getVideos(@Path("tv_id") int tvId, @Query("api_key") String apiKey);

    @GET(Constants.REVIEWS_URL)
    Call<MovieReviewResponse> getReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET(Constants.TV_REVIEWS_URL)
    Call<TvReviewResponse> getTvReviews(@Path("tv_id") int tvId, @Query("api_key") String apiKey);

    @GET(Constants.PERSON_URL)
    Call<Person> getPersonDetails(@Path("person_id") int personId, @Query("api_key") String apiKey);

    @GET(Constants.POPULAR_TVS_URL)
    Call<TvResponse> getPopularTvs(@Query("api_key") String apiKey, @Query("page") int page);

    @GET(Constants.TOP_RATED_TVS_URL)
    Call<TvResponse> getTopRatedTvs(@Query("api_key") String apiKey, @Query("page") int page);


}