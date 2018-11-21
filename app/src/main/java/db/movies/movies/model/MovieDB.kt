package db.movies.movies.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "movie")
class MovieDB (
        @PrimaryKey
        @ColumnInfo(name="id")
        val id: Int,
        @ColumnInfo(name="overview")
        val overview: String?,
        @ColumnInfo(name="poster")
        val posterPath: String?,
        @ColumnInfo(name="title")
        val title: String?,
        @ColumnInfo(name="vote_average")
        val voteAverage: Double?
)