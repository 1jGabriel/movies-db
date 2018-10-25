package db.movies.movies.model

import db.movies.movies.contract.MoviesContract
import db.movies.movies.remotey.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesListModel : MoviesContract.Model {
    override fun getMovies(onFinishedListener: MoviesContract.Model.onFinishedListener, page: Int, genre: Int) {
        RetrofitClient.getClient()
                .getMoviesByGenre(genre, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    onFinishedListener.onFinished(it.body()?.movies ?: arrayListOf())
                },{
                    onFinishedListener.onFailure(it)
                })
    }


}