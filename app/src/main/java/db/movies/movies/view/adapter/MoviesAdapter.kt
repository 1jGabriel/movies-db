package db.movies.movies.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import db.movies.movies.BuildConfig
import db.movies.movies.R
import db.movies.movies.model.Movie
import db.movies.movies.view.fragment.MoviesDelegate
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlin.properties.Delegates

class MoviesAdapter(val delegates : MoviesDelegate) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    var movies by Delegates.observable(ArrayList<Movie>()) { _, _, _ ->
        this.notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.movie_list_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleMovie.text = movie.title
        Picasso.get()
                .load(BuildConfig.SERVER_URL_IMAGE + movie.poster_path).error(R.drawable.ic_movies)
                .into(holder.posterMovie)

        holder?.itemView.setOnClickListener {
            delegates.detailMovie(movie)
        }

    }

    inner class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val posterMovie = view.movie_img
        val titleMovie = view.movie_name
    }
}