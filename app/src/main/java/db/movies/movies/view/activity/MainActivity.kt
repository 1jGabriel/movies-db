package db.movies.movies.view.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.widget.ImageView
import db.movies.movies.R
import db.movies.movies.view.adapter.GenresTabAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = GenresTabAdapter(supportFragmentManager)
        viewpager.adapter = adapter

        tabLayout.setupWithViewPager(viewpager)
        val tab = tabLayout.getTabAt(0)
        tab?.select()

        var searchIcon = include.findViewById<ImageView>(R.id.search_icon)

        searchIcon.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

    }


    private fun getMovies() {
        if (hasConnection()) {

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
        dialogLogout.setTitle(getString(R.string.atencao))
        dialogLogout.setMessage(getString(R.string.sem_conexao))
        dialogLogout.setPositiveButton(getString(R.string.tente_novamente)
        ) { _, _ -> onResume() }
        dialogLogout.setNegativeButton(android.R.string.cancel) { _, _ -> finish() }
        dialogLogout.show()
    }

}
