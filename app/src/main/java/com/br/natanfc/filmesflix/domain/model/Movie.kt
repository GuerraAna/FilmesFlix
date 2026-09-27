package com.br.natanfc.filmesflix.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val titulo: String,
    val imagem: String,
    val descricao: String?,
    val dataLancamento: String?
) : Parcelable
