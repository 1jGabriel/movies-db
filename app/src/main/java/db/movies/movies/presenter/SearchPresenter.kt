package db.movies.movies.presenter

import db.movies.movies.contract.SearchContract
import db.movies.movies.model.Movie
import db.movies.movies.model.SearchListModel

class SearchPresenter(val searchView : SearchContract.View) : SearchContract.Presenter, SearchContract.Model.OnFinishedListener{
    private var model : SearchContract.Model = SearchListModel()
    override fun onFinished(movies: ArrayList<Movie>, page: Int, totalPages: Int) {
        searchView.setDataToRecyclerView(movies, page, totalPages)
    }

    override fun onFailure(t: Throwable) {
        searchView.onResponseFailure(t)
    }

    override fun getMoreData(page: Int, query: String) {
        model.searchMovies(this, page, query)
    }

    override fun requestDataFromServer(query: String) {
        model.searchMovies(this, page = 1, query = query)
    }

}