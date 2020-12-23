package com.komangss.submissionjetpack.framework.mapper

import org.junit.Test
import java.util.regex.Pattern

class GenreIdMapperTest {

    @Test
    fun p() {
        val genreIdsToTest = "[16, 14, 12, 35, 10751]"
        val m = Pattern.compile("\\d+").matcher(genreIdsToTest)
        val result : ArrayList<Int> = ArrayList()
        while (m.find()) {
            result.add(m.group().toInt())
        }
        assert(result.size > 0)
    }
}