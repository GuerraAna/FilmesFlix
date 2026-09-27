package com.br.natanfc.filmesflix.presentation.detail

import org.junit.Assert.assertEquals
import org.junit.Test

class DateFormatterTest {

    @Test
    fun `valid api date is formatted as dd_MM_yyyy`() {
        assertEquals("26/04/2018", DateFormatter.format("26/04/2018"))
    }

    @Test
    fun `null date returns unavailable`() {
        assertEquals(DateFormatter.DATE_UNAVAILABLE, DateFormatter.format(null))
    }

    @Test
    fun `blank date returns unavailable`() {
        assertEquals(DateFormatter.DATE_UNAVAILABLE, DateFormatter.format("   "))
    }

    @Test
    fun `unparseable date returns raw value`() {
        assertEquals("2018-04-26", DateFormatter.format("2018-04-26"))
    }
}
