package com.br.natanfc.filmesflix.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterUrl: String,
    val overview: String?,
    val releaseDate: String?
)
