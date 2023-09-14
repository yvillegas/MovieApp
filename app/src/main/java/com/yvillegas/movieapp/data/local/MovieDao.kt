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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(movie: MovieEntity)

    /*@Query("UPDATE MovieEntity SET favorite = :flag WHERE id = :id")
    suspend fun addFavorite(id: String,flag:String)

    @Query("UPDATE MovieEntity SET favorite = 'false' WHERE id = :id")
    suspend fun deleteFavorite(id: String)*/

    @Query("SELECT COUNT(*) FROM MovieEntity WHERE id = :id")
    fun isFavorite(id: Int):Int

    @Query("DELETE FROM MovieEntity WHERE id = :id")
    fun delFavoriteMovies(id: Int)
}