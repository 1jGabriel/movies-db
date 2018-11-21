package db.movies.movies.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import db.movies.movies.R
import db.movies.movies.contract.SearchContract
import db.movies.movies.model.Movie
import db.movies.movies.presenter.SearchPresenter
import db.movies.movies.utils.InfiniteScrollListener
import db.movies.movies.view.adapter.MoviesAdapter
import db.movies.movies.view.fragment.MoviesDelegate
import kotlinx.android.synthetic.main.activity_search.*
import setSafeOnClickListener
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity(), MoviesDelegate, SearchContract.View {
    override fun unfavoriteMovie(movie: Movie) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun favoriteMovie(movie: Movie) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var moviesAdapter: MoviesAdapter
    private var offset = 1
    private var totalPages = 1

    private var searchPresenter = SearchPresenter(this)

    override fun detailMovie(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movieClicked", movie)
        startActivity(intent)
    }

    private fun initView(){
        moviesAdapter = MoviesAdapter(this, true)
        moviesAdapter.movies = ArrayList()
        movies_list.adapter = moviesAdapter
        movies_list.layoutManager = GridLayoutManager(this, 2)
        movies_list.itemAnimator = DefaultItemAnimator()
        //fav_icon.visibility = View.GONE
    }
    override fun setDataToRecyclerView(movies: ArrayList<Movie>, page: Int, totalPages: Int) {
        setParametersPagination(page, totalPages)
        moviesAdapter.movies.addAll(movies)
        moviesAdapter.notifyDataSetChanged()
    }

    private fun setParametersPagination(page: Int, totalPages: Int) {
        offset = page
        this.totalPages = totalPages
    }

    override fun onResponseFailure(t: Throwable) {
        Toast.makeText(this, "Não foi possível obter os dados.", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setListeners()
        initView()
    }

    private fun setListeners(){
        RxTextView.textChanges(text_input_busca)
                .debounce(1, TimeUnit.SECONDS).subscribe{
                    moviesAdapter.movies.clear()
                    offset = 1
                    totalPages = 1
                    if(it.length > 2){
                        this.getDataFromServer()
                    }
                }
        back_btn.setSafeOnClickListener {
            finish()
        }
        clear_btn.setSafeOnClickListener {
            text_input_busca.text.clear()
            moviesAdapter.movies.clear()
        }
        movies_list.addOnScrollListener(object : InfiniteScrollListener(){
            override fun onLoadMore() {
                verifyPagination(offset, totalPages)
            }

            private fun verifyPagination(offset: Int, totalPages: Int) {
                if (offset < totalPages) {
                    if (moviesAdapter.movies.size == 0) {
                        getDataFromServer()
                    } else {
                        searchPresenter.getMoreData(offset +1 , text_input_busca.text.toString())
                    }
                }
            }
        })
    }
    private fun getDataFromServer() {
        searchPresenter.requestDataFromServer(text_input_busca.text.toString())
    }
}
