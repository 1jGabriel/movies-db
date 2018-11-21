package db.movies.movies.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import db.movies.movies.R
import db.movies.movies.model.Movie
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

        var favouritesIcon = include.findViewById<ImageButton>(R.id.fav_icon)
        favouritesIcon.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            this.listaFavoritos = listaFavoritos
            intent.putExtra("favorites", listaFavoritos)
            startActivity(intent)
        }

    }
}
