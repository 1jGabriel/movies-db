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
    private var totalPages = 1

    private var moviesPresenter = MoviesPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_movies, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListeners()
        getDataFromServer()
    }

    private fun getDataFromServer() {
        moviesPresenter.requestDataFromServer(idGenre.id)
    }

    private fun initView(){
        moviesAdapter = MoviesAdapter(this)
        moviesAdapter.movies = ArrayList()
        movies_list.adapter = moviesAdapter
        movies_list.layoutManager = GridLayoutManager(context, 2)
        movies_list.itemAnimator = DefaultItemAnimator()
    }

    private fun setListeners(){
        movies_list.addOnScrollListener(object : InfiniteScrollListener(){
            override fun onLoadMore() {
                verifyPagination(offset, totalPages)
            }

            private fun verifyPagination(offset: Int, totalPages: Int) {
                if (offset < totalPages) {
                    if (moviesAdapter.movies.size == 0) {
                        getDataFromServer()
                    } else {
                        moviesPresenter.getMoreData(offset +1 , idGenre.id)
                    }
                }
            }
        })
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

    override fun showProgress() {
        showView(loading)
    }

    override fun hideProgress() {
        hideView(loading)
    }

    override fun setDataToRecyclerView(movies: ArrayList<Movie>, page: Int, totalPages: Int, genre: Int) {
        setParametersPagination(page, totalPages)
        moviesAdapter.movies.addAll(movies)
        moviesAdapter.notifyDataSetChanged()
        hideProgress()
    }

    private fun setParametersPagination(page: Int, totalPages: Int) {
        offset = page
        this.totalPages = totalPages
    }

    override fun onResponseFailure(t: Throwable) {
        Toast.makeText(context, "Não foi possível obter os dados.", Toast.LENGTH_LONG).show()
    }
}