package db.movies.movies.view.activity

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.Log
import db.movies.movies.R
import db.movies.movies.enums.GenresMoviesEnum
import db.movies.movies.remote.MoviesService
import db.movies.movies.remote.RetrofitClient
import db.movies.movies.view.adapter.GenresTabAdapter
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = GenresTabAdapter(supportFragmentManager, this, GenresMoviesEnum.ACAO.id)

        viewpager.adapter = adapter

        tabLayout.setupWithViewPager(viewpager)
        val tab = tabLayout.getTabAt(0)
        tab?.select()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        getMovies()
    }

    private fun getMovies() {
        if (hasConnection()) {
            RetrofitClient.getRetrofit().create(MoviesService::class.java)
                    .getMoviesByGenre(GenresMoviesEnum.ACAO.id, 1).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { print("INICIOU") }
                    .doFinally { print("Finalizou") }
                    .subscribe {
                        if (it.isSuccessful) Log.i("Teste", it.body().toString())
                        else Log.i("Teste", it.errorBody().toString())
                    }
        } else {
            alertNoConnection()
        }
    }

    private fun hasConnection(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return (activeNetwork != null &&
                (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE))
    }

    private fun alertNoConnection() {
        val dialogLogout = AlertDialog.Builder(this)
        dialogLogout.setTitle("Atenção")
        dialogLogout.setMessage("Sem conexão")
        dialogLogout.setPositiveButton("Tente novamente",
                { _, _ -> onResume() })
        dialogLogout.setNegativeButton(android.R.string.cancel, { _, _ -> finish() })
        dialogLogout.show()
    }
}
