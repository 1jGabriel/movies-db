package db.movies.movies.view.activity

import android.os.Bundle
import com.squareup.picasso.Picasso
import db.movies.movies.BuildConfig
import db.movies.movies.R
import db.movies.movies.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.extras["movieClicked"] as Movie

        description_txt.text = movie.overview

        Picasso.get().load(BuildConfig.SERVER_URL_IMAGE + movie.posterPath).into(poster_movie)

        title = movie.title
    }
}
