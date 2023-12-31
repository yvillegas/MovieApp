package com.yvillegas.movieapp.domain

import com.google.gson.GsonBuilder
import com.yvillegas.movieapp.application.AppConstants
import com.yvillegas.movieapp.data.model.Cast
import com.yvillegas.movieapp.data.model.CastList
import com.yvillegas.movieapp.data.model.MovieList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int, @Query("api_key") apiKey: String): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int,@Query("api_key") apiKey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int,@Query("api_key") apiKey: String): MovieList

    @GET("movie/{id}/credits")
    suspend fun getCastMovie(@Path("id") id: String, @Query("api_key") apiKey: String): CastList
}

object RetrofitClient {
    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}