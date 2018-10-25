package db.movies.movies.view.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import db.movies.movies.enums.GenresMoviesEnum
import db.movies.movies.view.fragment.MoviesFragment

class GenresTabAdapter (fm: FragmentManager, context: Context, id: Int) : FragmentPagerAdapter(fm)  {

    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> MoviesFragment.newInstance(GenresMoviesEnum.ACAO.id)
        1 -> MoviesFragment.newInstance(GenresMoviesEnum.DRAMA.id)
        2 -> MoviesFragment.newInstance(GenresMoviesEnum.FANTASIA.id)
        3 -> MoviesFragment.newInstance(GenresMoviesEnum.FICCAO.id)
        else -> MoviesFragment.newInstance(GenresMoviesEnum.ACAO.id)
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> GenresMoviesEnum.ACAO.title
        1 -> GenresMoviesEnum.DRAMA.title
        2 -> GenresMoviesEnum.FANTASIA.title
        3-> GenresMoviesEnum.FICCAO.title
        else -> GenresMoviesEnum.ACAO.title
    }

    override fun getCount(): Int = 4
}