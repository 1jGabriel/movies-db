package db.movies.movies.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Movie (
        @SerializedName("id") val id: Int,
        @SerializedName("overview") val overview: String?,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("vote_average") val voteAverage: Double?,
        var favorite : Boolean = false
) : Serializable