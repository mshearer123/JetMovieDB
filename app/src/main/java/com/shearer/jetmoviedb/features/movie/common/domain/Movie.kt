package com.shearer.jetmoviedb.features.movie.common.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movies")
data class Movie(
        @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("title")
        val title: String,
        @SerializedName("genres")
        val genres: String,
        @SerializedName("popularity")
        val popularity: String,
        @SerializedName("releaseYear")
        val releaseYear: String,
        @SerializedName("posterUrl")
        val posterUrl: String,
        @SerializedName("type")
        var type: String? = null,
        @SerializedName("movie_id")
        val movieId: Int
) : Serializable {
    var page: Int = -1

}

