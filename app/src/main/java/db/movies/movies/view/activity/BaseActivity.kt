package db.movies.movies.view.activity


import android.support.v7.app.AppCompatActivity
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBar
import android.util.TypedValue
import db.movies.movies.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


    }

}
