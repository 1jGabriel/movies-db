package db.movies.movies.contract

import db.movies.movies.model.Movie

interface SearchContract {
    interface Model{
        interface OnFinishedListener{
            fun onFinished(movies: ArrayList<Movie>, page: Int, totalPages: Int)
            fun onFailure(t : Throwable)
        }
        fun searchMovies(OnFinishedListener : OnFinishedListener, page : Int, query: String)
    }

    interface View{
        fun setDataToRecyclerView(movies: ArrayList<Movie>, page: Int, totalPages: Int)
        fun onResponseFailure(t: Throwable)

    }

    interface Presenter {
        fun getMoreData(page: Int, query: String)
        fun requestDataFromServer(query : String)
    }
}