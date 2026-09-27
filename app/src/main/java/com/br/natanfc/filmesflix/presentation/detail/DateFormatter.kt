package com.br.natanfc.filmesflix.presentation.detail

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {

    const val DATE_UNAVAILABLE = "Data indisponível"
    private const val API_PATTERN = "dd/MM/yyyy"
    private const val DISPLAY_PATTERN = "dd/MM/yyyy"

    fun format(apiDate: String?): String {
        if (apiDate.isNullOrBlank()) return DATE_UNAVAILABLE
        val parsed = runCatching {
            SimpleDateFormat(API_PATTERN, Locale("pt", "BR")).apply { isLenient = false }
                .parse(apiDate)
        }.getOrNull()
        return if (parsed != null) {
            SimpleDateFormat(DISPLAY_PATTERN, Locale("pt", "BR")).format(parsed)
        } else {
            apiDate
        }
    }
}
