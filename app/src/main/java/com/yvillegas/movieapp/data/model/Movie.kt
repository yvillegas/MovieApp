package com.yvillegas.movieapp.data.model

import com.google.gson.annotations.SerializedName
import com.yvillegas.movieapp.data.model.entities.MovieEntity

data class MovieList(

    val results: MutableList<Movie>,

    var page: Int? = 1,

    @SerializedName("total_pages")
    val totalPages: Int? = 0,

    @SerializedName("total_results")
    val totalResults: Int? = 0
)

data class Movie(

    val id: Int = -1,

    val adult: Boolean = false,

    @SerializedName("backdrop_path")
    val backdropPath: String = "",

    @SerializedName("original_title")
    val originalTitle: String = "",

    @SerializedName("original_language")
    val originalLanguage: String = "",

    val overview: String = "",

    val popularity: Double = -1.0,

    @SerializedName("poster_path")
    val posterPath: String = "",

    @SerializedName("release_date")
    val releaseDate: String = "",

    val video: Boolean = false,

    @SerializedName("vote_average")
    val voteAverage: Double = -1.0,

    @SerializedName("vote_count")
    val voteCount: Int = -1,

    @SerializedName("movie_type")
    var movieType: String = "",

)

fun Movie.toMovieEntity(movieType: String): MovieEntity = MovieEntity(
    this.id,
    this.adult,
    this.backdropPath ?: "",
    this.originalTitle,
    this.originalLanguage,
    this.overview,
    this.popularity,
    this.posterPath,
    this.releaseDate,
    this.video,
    this.voteAverage,
    this.voteCount,
    movieType = movieType
)
