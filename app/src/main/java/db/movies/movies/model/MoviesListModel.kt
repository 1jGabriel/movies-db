package db.movies.movies.model

import db.movies.movies.contract.MoviesContract
import db.movies.movies.remotey.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesListModel : MoviesContract.Model {
    override fun getMovies(OnFinishedListener: MoviesContract.Model.OnFinishedListener, page: Int, genre: Int) {
        RetrofitClient.getClient()
                .getMoviesByGenre(genre, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    OnFinishedListener.onFinished(
                            it.body()?.movies ?: arrayListOf(),
                            it.body()?.page ?: 1,
                            it.body()?.totalPages ?: 1,
                            genre)
                },{
                    OnFinishedListener.onFailure(it)
                })
    }


}