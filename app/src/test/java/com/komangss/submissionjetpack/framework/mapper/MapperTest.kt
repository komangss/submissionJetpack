package com.komangss.submissionjetpack.framework.mapper

import org.junit.Assert
import org.junit.Test
import java.util.regex.Pattern

class MapperTest {

    @Test
    fun `test convert example id from entity into domain model using regex`() {
        val genreIdsToTest = "[16, 14, 12, 35, 10751]"
        val expectedResult = listOf(16, 14, 12, 35, 10751)

        val m = Pattern.compile("\\d+").matcher(genreIdsToTest)
        val result: ArrayList<Int> = ArrayList()
        while (m.find()) {
            result.add(m.group().toInt())
        }
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `test convert example id from entity into domain model using kotlin function`() {
        var originalCountries = "[16, 14, 12, 35, 10751]"
        val expectedResult = listOf(16, 14, 12, 35, 10751)

        originalCountries = originalCountries.replace(" ", "")
        originalCountries = originalCountries.substring(1, originalCountries.length - 1)

        val result: List<Int> = originalCountries.split(",").map {
            it.toInt()
        }

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `test convert example original country from entity into domain model`() {
        var originalCountries = "[en, uk, id]"
        val expectedResult = listOf("en", "uk", "id")

        originalCountries = originalCountries.replace(" ", "")
        originalCountries = originalCountries.substring(1, originalCountries.length - 1)

        val result: List<String> = originalCountries.split(",")
        Assert.assertEquals(expectedResult, result)
    }
}