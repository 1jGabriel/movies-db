package db.movies.movies.view.activity

import android.os.Bundle
import com.squareup.picasso.Picasso
import db.movies.movies.BuildConfig
import db.movies.movies.R
import db.movies.movies.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*
import android.content.Intent



class DetailActivity : BaseActivity() {

    lateinit var movie : Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        movie = intent.extras["movieClicked"] as Movie

        description_txt.text = movie.overview

        Picasso.get().load(BuildConfig.SERVER_URL_IMAGE + movie.posterPath).into(poster_movie)

        title = movie.title

        share_btn.setOnClickListener {
            shareMovie()
        }




    }

    private fun shareMovie() {
        val movie_imdb = "http://www.imdb.com/title/"+ movie.id
        if (movie.title != null || movie.voteAverage!=null|| !movie.id.equals(null)) {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "text/plain"
            myIntent.putExtra(Intent.EXTRA_TEXT, "*${movie.title}*\n\n${movie.overview}\n\n${"Classificação : " + movie.voteAverage}\n")
            startActivity(Intent.createChooser(myIntent, "Share with"))
        }
    }
}
