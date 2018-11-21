package db.movies.movies.presenter

import db.movies.movies.contract.MoviesContract
import db.movies.movies.model.Movie
import db.movies.movies.model.MoviesListModel

class MoviesPresenter(val view : MoviesContract.View) : MoviesContract.Presenter, MoviesContract.Model.OnFinishedListener{

    private var model : MoviesContract.Model = MoviesListModel()

    override fun onFinished(movies: ArrayList<Movie>, page: Int, totalPages: Int, genre: Int) {
        view.setDataToRecyclerView(movies, page, totalPages, genre)
        view.hideRefresh()
    }

    override fun onFailure(t: Throwable) {
        view.onResponseFailure(t)
        view.hideProgress()
    }

    override fun getMoreData(page: Int, genre: Int) {
        model.getMovies(this, page, genre)
    }

    override fun requestDataFromServer(genre: Int) {
        view.showProgress()
        model.getMovies(this, page = 1, genre = genre)
    }

}
