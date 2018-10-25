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
import db.movies.movies.utils.InfiniteScrollListener
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
    private var offset = 1
    private var totalPaginas = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_movies, container, false)

    override fun onResume() {
        super.onResume()

        moviesAdapter = MoviesAdapter(this)

        moviesAdapter.movies = ArrayList()

        movies_list.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.layoutManager = GridLayoutManager(context, 2)
            this.adapter = moviesAdapter

        }

        movies_list.adapter!!.notifyDataSetChanged()

        movies_list.addOnScrollListener(object : InfiniteScrollListener(){
            override fun onLoadMore() {
                if(offset < totalPaginas){
                    offset++
                    presenterGetData()
                }
            }
        })
        //mover pro presenters
        presenterGetData()
    }

    private fun presenterGetData() {
        RetrofitClient.getRetrofit().create(MoviesService::class.java)
                .getMoviesByGenre(idGenre.id, offset).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { "startloading" }
                .doFinally { "fim loading" }
                .subscribe {
                    if (it.isSuccessful) {
                        moviesAdapter.movies.addAll(it.body()?.movies!!)
                        offset = it?.body()!!.page
                        totalPaginas = it?.body()!!.total_pages
                        moviesAdapter.notifyDataSetChanged()

                    } else Log.i("Teste", it.errorBody().toString())
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance(idGenre: Int): MoviesFragment {
            val fragment = MoviesFragment()
            fragment.offset = 1
            fragment.totalPaginas =1
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