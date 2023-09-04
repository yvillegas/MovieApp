package com.yvillegas.movieapp.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yvillegas.movieapp.data.model.Movie
import com.yvillegas.movieapp.data.model.MovieList

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Int? = -1,
    @ColumnInfo(name = "adult")
    val adult: Boolean? = false,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String = " ",
    @ColumnInfo(name = "original_title")
    val originalTitle: String? = "",
    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = "",
    @ColumnInfo(name = "overview")
    val overview: String? = "",
    @ColumnInfo(name = "popularity")
    val popularity: Double? = -1.0,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = "",
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = "",
    @ColumnInfo(name = "video")
    val video: Boolean? = false,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = -1.0,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = -1,
    @ColumnInfo(name = "movie_type")
    val movieType: String = "",
    @ColumnInfo(name = "favorite")
    val favorite: Boolean? = false
)

fun MovieEntity.toMovie(): Movie = Movie(
    this.id,
    this.adult,
    this.backdropPath,
    this.originalTitle,
    this.originalLanguage,
    this.overview,
    this.popularity,
    this.posterPath,
    this.releaseDate,
    this.video,
    this.voteAverage,
    this.voteCount,
    this.movieType,
    this.favorite,
)

fun List<MovieEntity>.toMovieList(): MovieList {
    val resultList = mutableListOf<Movie>()
    this.forEach { movieEntity ->
        resultList.add(movieEntity.toMovie())
    }
    return MovieList(resultList)
}
