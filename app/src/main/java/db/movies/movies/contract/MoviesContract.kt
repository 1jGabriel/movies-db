package db.movies.movies.contract

import db.movies.movies.model.Movie

interface MoviesContract {
    interface Model{
        interface OnFinishedListener{
            fun onFinished(movies: ArrayList<Movie>, page: Int, totalPages: Int, genre: Int)
            fun onFailure(t : Throwable)
        }
        fun getMovies(OnFinishedListener : OnFinishedListener, page : Int, genre : Int)
    }

    interface View{
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(movies: ArrayList<Movie>, page: Int, totalPages: Int, genre: Int)
        fun onResponseFailure(t: Throwable)
        fun hideRefresh()

    }

    interface Presenter {
        fun getMoreData(page: Int, genre: Int)
        fun requestDataFromServer(genre: Int)
    }
}