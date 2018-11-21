package db.movies.movies.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insertMovie(movie: MovieDB)

    @Update
    fun updateHumor(movie: MovieDB)

    @Delete
    fun deleteHumor(movie: MovieDB)

    @Query("SELECT * FROM movie")
    fun getHumor(): List<MovieDB>

}