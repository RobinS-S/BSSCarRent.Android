package com.bss.carrent.misc

import com.bss.carrent.data.user.UserDto
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate

@RunWith(JUnit4::class)
class HelpersTest {

    @Test
    fun testFormatShortDate() {
        val expected = LocalDate.of(2023, 1, 1)
        assertEquals(expected, Helpers.parseShortDate("01-01-2023"))
    }

    @Test
    fun testGetFormattedName() {
        val userDto = UserDto(0, "Henk", null, "Testers", "+31", "612345678")
        assertEquals("Henk Testers", Helpers.getFormattedName(userDto))
    }

    @Test
    fun testFormatDoubleWithOptionalDecimals() {
        assertEquals("1", Helpers.formatDoubleWithOptionalDecimals(1.0))
        assertEquals("1.50", Helpers.formatDoubleWithOptionalDecimals(1.5))
    }
}