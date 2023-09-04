package com.yvillegas.movieapp.data.model

import com.google.gson.annotations.SerializedName
import com.yvillegas.movieapp.data.model.entities.MovieEntity

data class MovieList(

    val results: List<Movie>? = listOf(),

    val page: Long? = null,

    @SerializedName("total_pages")
    val totalPages: Long? = null,

    @SerializedName("total_results")
    val totalResults: Long? = null
)

data class Movie(

    val id: Int? = null,

    val adult: Boolean? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    val overview: String? = null,

    val popularity: Double? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    val video: Boolean? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null,

    @SerializedName("movie_type")
    val movieType: String? = null,

    @SerializedName("favorite")
    val favorite: Boolean? = false
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
    movieType = movieType,
    this.favorite
)