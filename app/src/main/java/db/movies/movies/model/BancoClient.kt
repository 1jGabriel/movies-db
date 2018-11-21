package db.movies.movies.model

import android.arch.persistence.room.Room
import android.content.Context

class BancoClient  {
    companion object {
        fun getDatabase(context: Context) : BancoLocal =
                Room.databaseBuilder(context.applicationContext, BancoLocal::class.java, "local_storage").build()
    }
}