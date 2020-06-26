package com.streammovietv.networking;

import com.streammovietv.model.MovieCreditResponse;
import com.streammovietv.model.MovieLang;
import com.streammovietv.model.MovieResponse;
import com.streammovietv.model.MovieReviewResponse;
import com.streammovietv.model.MovieSimilarResponse;
import com.streammovietv.model.MovieTrailerResponse;
import com.streammovietv.model.Person;
import com.streammovietv.model.SearchResponse;
import com.streammovietv.model.TvCreditResponse;
import com.streammovietv.model.TvLang;
import com.streammovietv.model.TvResponse;
import com.streammovietv.model.TvReviewResponse;
import com.streammovietv.model.TvSimilarResponse;
import com.streammovietv.model.TvVideoResponse;
import com.streammovietv.utilities.Constants;

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
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String Language);

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

    @GET(Constants.MOVIE_SIMILAR_URL)
    Call<MovieSimilarResponse> getSimilar(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("page") int page);

    @GET(Constants.TV_SIMILAR_URL)
    Call<TvSimilarResponse> getTvSimilar(@Path("movie_id") int tvId, @Query("api_key") String apiKey, @Query("page") int page);

    @GET(Constants.PERSON_URL)
    Call<Person> getPersonDetails(@Path("person_id") int personId, @Query("api_key") String apiKey);

    @GET(Constants.POPULAR_TVS_URL)
    Call<TvResponse> getPopularTvs(@Query("api_key") String apiKey, @Query("page") int page);

    @GET(Constants.TOP_RATED_TVS_URL)
    Call<TvResponse> getTopRatedTvs(@Query("api_key") String apiKey, @Query("page") int page);


}