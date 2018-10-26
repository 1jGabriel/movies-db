package db.movies.movies.model

import db.movies.movies.contract.SearchContract
import db.movies.movies.remotey.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchListModel : SearchContract.Model{

    override fun searchMovies(OnFinishedListener: SearchContract.Model.OnFinishedListener, page: Int, query: String) {
        RetrofitClient.getClient().searchMovies(query, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    OnFinishedListener.onFinished(
                            it.body()?.movies ?: arrayListOf(),
                            it.body()?.page ?: 1,
                            it.body()?.totalPages ?: 1
                    )
                },{
                    OnFinishedListener.onFailure(it)
                })
    }
}