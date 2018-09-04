package com.shearer.jetmoviedb.features.movie.common.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movies")
data class Movie(
        @PrimaryKey
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
        val type: String,
        @SerializedName("id")
        val id: Int) : Serializable {
    var page: Int = -1

    companion object {
        val TAG = "MOVIE"
    }
}

