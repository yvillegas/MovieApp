package com.yvillegas.movieapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yvillegas.movieapp.data.model.entities.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    suspend fun getAllMovies():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieEntity)

    @Query("UPDATE MovieEntity SET favorite = 'true' WHERE id = :id")
    suspend fun addFavorite(id: String){}

    @Query("UPDATE MovieEntity SET favorite = 'false' WHERE id = :id")
    suspend fun deleteFavorite(id: String)
}