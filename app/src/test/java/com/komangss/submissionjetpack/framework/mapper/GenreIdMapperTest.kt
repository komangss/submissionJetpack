package com.komangss.submissionjetpack.framework.mapper

import org.junit.Assert
import org.junit.Test
import java.util.regex.Pattern

class GenreIdMapperTest {

    @Test
    fun `test convert example id from entity into domain model using regex`() {
        val genreIdsToTest = "[16, 14, 12, 35, 10751]"
        val m = Pattern.compile("\\d+").matcher(genreIdsToTest)
        val result: ArrayList<Int> = ArrayList()
        while (m.find()) {
            result.add(m.group().toInt())
        }
        assert(result.size > 0)
    }

    @Test
    fun `test convert example id from entity into domain model using kotlin function`() {
        var genreIdsToTest = "[16, 14, 12, 35, 10751]"
        genreIdsToTest = genreIdsToTest.replace(" ", "")
        genreIdsToTest = genreIdsToTest.substring(1, genreIdsToTest.length - 1)

        val expectedResult = listOf(16, 14, 12, 35, 10751)

        val result: List<Int> = genreIdsToTest.split(",").map {
            it.toInt()
        }

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `test convert example original country from entity into domain model`() {
        var genreIdsToTest = "[en, uk, id]"
        genreIdsToTest = genreIdsToTest.replace(" ", "")
        genreIdsToTest = genreIdsToTest.substring(1, genreIdsToTest.length - 1)


        val expectedResult = listOf("en", "uk", "id")

        val result: List<String> = genreIdsToTest.split(",")
        Assert.assertEquals(expectedResult, result)
    }


}