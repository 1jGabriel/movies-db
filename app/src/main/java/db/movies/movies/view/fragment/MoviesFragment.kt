package db.movies.movies.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import db.movies.movies.R
import db.movies.movies.contract.MoviesContract
import db.movies.movies.enums.GenresMoviesEnum
import db.movies.movies.model.Movie
import db.movies.movies.presenter.MoviesPresenter
import db.movies.movies.utils.InfiniteScrollListener
import db.movies.movies.utils.hideView
import db.movies.movies.utils.showView
import db.movies.movies.view.activity.DetailActivity
import db.movies.movies.view.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies.*


interface MoviesDelegate{
    fun detailMovie(movie: Movie)
}

class MoviesFragment : Fragment(), MoviesDelegate, MoviesContract.View {


    lateinit var idGenre : GenresMoviesEnum
    lateinit var moviesAdapter: MoviesAdapter
    private var offset = 1
    private var totalPaginas = 1
    private var moviesPresenter = MoviesPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)=
            inflater?.inflate(R.layout.fragment_movies, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListeners()
        moviesPresenter.requestDataFromServer(idGenre.id)
    }

    fun initView(){
        moviesAdapter = MoviesAdapter(this)
        moviesAdapter.movies = ArrayList()
        movies_list.adapter = moviesAdapter
        movies_list.layoutManager = GridLayoutManager(context, 2)
        movies_list.itemAnimator = DefaultItemAnimator()
    }

    fun setListeners(){
        movies_list.addOnScrollListener(object : InfiniteScrollListener(){
            override fun onLoadMore() {
                if(offset < totalPaginas){
                    offset++
                    moviesPresenter.getMoreData(offset, idGenre.id)
                }
            }
        })
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

    override fun showProgress() {
        showView(loading)
    }

    override fun hideProgress() {
        hideView(loading)
    }

    override fun setDataToRecyclerView(movies: ArrayList<Movie>) {
        moviesAdapter.movies.addAll(movies)
        moviesAdapter.notifyDataSetChanged()
        hideProgress()
    }

    override fun onResponseFailure(t: Throwable) {
        Toast.makeText(context, "Não foi possível obter os dados.", Toast.LENGTH_LONG).show()
    }
}