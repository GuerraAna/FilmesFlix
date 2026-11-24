package com.br.natanfc.filmesflix.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY title")
    fun observeMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun observeMovie(id: Int): Flow<MovieEntity?>

    @Upsert
    suspend fun upsert(entity: MovieEntity)

    @Transaction
    suspend fun upsertSummaries(entities: List<MovieEntity>) =
        entities.forEach { upsertSummaryFields(it.id, it.title, it.posterUrl) }

    // Summary payloads have no overview/releaseDate: partial upsert keeps the
    // detail columns already fetched instead of nulling them out.
    @Query(
        """INSERT INTO movies (id, title, posterUrl) VALUES (:id, :title, :posterUrl)
           ON CONFLICT(id) DO UPDATE SET title = excluded.title, posterUrl = excluded.posterUrl"""
    )
    suspend fun upsertSummaryFields(id: Int, title: String, posterUrl: String)
}
