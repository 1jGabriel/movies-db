package db.movies.movies.view.activity


import android.support.v7.app.AppCompatActivity
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.chibatching.kotpref.Kotpref
import db.movies.movies.model.Movie

abstract class BaseActivity : AppCompatActivity(){
    var listaFavoritos = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
