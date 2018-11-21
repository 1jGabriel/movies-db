package db.movies.movies.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import db.movies.movies.R
import db.movies.movies.model.Movie
import db.movies.movies.view.adapter.MoviesAdapter
import db.movies.movies.view.fragment.MoviesDelegate
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : BaseActivity(), MoviesDelegate {
    override fun favoriteMovie(movie: Movie) {

    }

    override fun unfavoriteMovie(movie: Movie) {
        moviesAdapter.movies.remove(movie)
        moviesAdapter.notifyDataSetChanged()
    }

    var lista = arrayListOf<Movie>()
    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        moviesAdapter = MoviesAdapter(this, false)
        moviesAdapter.movies = ArrayList()
        movies_list.adapter = moviesAdapter
        movies_list.layoutManager = GridLayoutManager(this, 2)
        movies_list.itemAnimator = DefaultItemAnimator()
        title = "Favoritos"

        if(intent.hasExtra("favorites")){
            lista.addAll(intent.extras["favorites"] as ArrayList<Movie>)
            setDataToRecyclerView(lista)
        }
    }

    fun setDataToRecyclerView(movies: ArrayList<Movie>) {
        moviesAdapter.movies.addAll(movies)
        moviesAdapter.notifyDataSetChanged()
    }

    override fun detailMovie(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movieClicked", movie)
        startActivity(intent)
    }

}
