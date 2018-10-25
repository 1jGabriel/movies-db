package db.movies.movies.view.activity

import android.app.Activity
import android.os.Bundle
import com.squareup.picasso.Picasso
import db.movies.movies.BuildConfig
import db.movies.movies.R
import db.movies.movies.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var movie = intent.extras["movie"] as Movie

        description_txt.text = movie.overview

        Picasso.get().load(BuildConfig.SERVER_URL_IMAGE + movie.poster_path).into(poster_movie)

        title= movie.title
    }
}
