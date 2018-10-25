package db.movies.movies.contract

import db.movies.movies.model.Movie

interface MoviesContract {

    interface Model{

        interface onFinishedListener{
            fun onFinished(movies : ArrayList<Movie> )
            fun onFailure(t : Throwable)
        }
        fun getMovies(onFinishedListener : onFinishedListener, page : Int, genre : Int)
    }

    interface View{
        fun showProgress()
        fun hideProgress()

        fun setDataToRecyclerView(movies: ArrayList<Movie>)

        fun onResponseFailure(t: Throwable)

    }

    interface Presenter {

        fun onDestroy()

        fun getMoreData(page: Int, genre: Int)

        fun requestDataFromServer(genre: Int)

    }
}