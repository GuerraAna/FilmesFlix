package com.br.natanfc.filmesflix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = true)
abstract class FilmesFlixDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
