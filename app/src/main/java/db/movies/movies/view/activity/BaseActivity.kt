package db.movies.movies.view.activity


import android.support.v7.app.AppCompatActivity
import android.content.pm.ActivityInfo
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
