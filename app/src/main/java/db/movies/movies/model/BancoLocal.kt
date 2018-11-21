package db.movies.movies.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [MovieDB::class], version = 1)
abstract class BancoLocal : RoomDatabase() {
    abstract fun MovieDAO(): MovieDAO
}