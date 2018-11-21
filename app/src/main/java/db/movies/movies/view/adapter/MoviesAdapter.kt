package db.movies.movies.view.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import db.movies.movies.BuildConfig
import db.movies.movies.R
import db.movies.movies.model.Movie
import db.movies.movies.view.fragment.MoviesDelegate
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlin.properties.Delegates

class MoviesAdapter(val delegates : MoviesDelegate, val search : Boolean?) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    var movies by Delegates.observable(ArrayList<Movie>()) { _, _, _ ->
        this.notifyDataSetChanged()
    }

    lateinit var  parent : ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        this.parent = parent
        return MoviesViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleMovie.text = movie.title

        if(search == true){
            holder.favorite.visibility = View.GONE
        }
        if (movie.favorite) {

            holder.favorite.background =
                    ContextCompat.getDrawable(parent.context, R.drawable.ic_star_yellow )

        } else {
            holder.favorite.background =
                    ContextCompat.getDrawable(parent.context, R.drawable.ic_star_border )

        }

        Picasso.get()
                .load(BuildConfig.SERVER_URL_IMAGE + movie.posterPath).error(R.drawable.ic_movies)
                .into(holder.posterMovie)

        holder.itemView.setOnClickListener {
            delegates.detailMovie(movie)
        }

        holder.favorite.setOnClickListener {

            if(movie.favorite){

                holder.favorite.background =
                        ContextCompat.getDrawable(parent.context, R.drawable.ic_star_border )
                movie.favorite = false
                delegates.unfavoriteMovie(movie)

            } else {
                it.background = ContextCompat.getDrawable(parent.context, R.drawable.ic_star_yellow )
                movie.favorite = true
                delegates.favoriteMovie(movie)
            }

        }
    }

    inner class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val posterMovie: ImageView = view.movie_img
        val titleMovie: TextView = view.movie_name
        val favorite : ImageButton = view.fav_icon
    }
}