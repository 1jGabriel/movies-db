package db.movies.movies.remote

import db.movies.movies.model.MovieResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*


interface MoviesService {

    @GET("discover/movie")
    fun getMoviesByGenre(@Query("with_genres")genre : Int,
                         @Query("page") page : Int): Observable<Response<MovieResponse>>

    @GET("search/movie")
    fun searchMovies(@Query("query")query : String ,
                     @Query("page") page : Int) : Observable<Response<MovieResponse>>
}