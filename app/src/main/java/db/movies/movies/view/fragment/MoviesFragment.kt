package db.movies.movies.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import db.movies.movies.R
import db.movies.movies.enums.GenresMoviesEnum
import db.movies.movies.model.Movie
import db.movies.movies.remote.MoviesService
import db.movies.movies.remote.RetrofitClient
import db.movies.movies.view.activity.DetailActivity
import db.movies.movies.view.adapter.MoviesAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movies.*


interface MoviesDelegate{
    fun detailMovie(movie: Movie)
}

class MoviesFragment : Fragment(), MoviesDelegate {
    lateinit var idGenre : GenresMoviesEnum
    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_movies, container, false)

    override fun onStart() {
        super.onStart()
        moviesAdapter.movies = ArrayList()
        movies_list.adapter!!.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MoviesAdapter(this)

        movies_list.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.layoutManager = GridLayoutManager(context, 2)
            this.adapter = moviesAdapter
        }


        //mover pro presenters
        RetrofitClient.getRetrofit().create(MoviesService::class.java)
                .getMoviesByGenre(idGenre.id, 1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { print("INICIOU") }
                .doFinally { print("Finalizou") }
                .subscribe {
                    if (it.isSuccessful) {
                        moviesAdapter.movies.addAll(it.body()?.movies!!)
                        moviesAdapter.notifyDataSetChanged()
                    }
                    else Log.i("Teste", it.errorBody().toString())
                }
    }

    companion object {
        fun newInstance(idGenre: Int): MoviesFragment {
            val fragment = MoviesFragment()
            fragment.idGenre = GenresMoviesEnum.valor(idGenre)
            return fragment
        }
    }

    override fun detailMovie(movie: Movie) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("movieClicked", movie)
        startActivity(intent)
    }
}