package com.shearer.jetmoviedb.features.movie.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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
        @SerializedName("thumbnailUrl")
        val thumbnailUrl: String) {
    var indexInResponse: Int = -1
}

