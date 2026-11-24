package com.br.natanfc.filmesflix.presentation.detail

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {

    const val DATE_UNAVAILABLE = "Data indisponível"
    private val apiFormat = DateTimeFormatter.ISO_LOCAL_DATE
    private val displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))

    fun format(apiDate: String?): String {
        if (apiDate.isNullOrBlank()) return DATE_UNAVAILABLE
        return runCatching {
            LocalDate.parse(apiDate, apiFormat).format(displayFormat)
        }.getOrDefault(apiDate)
    }
}
