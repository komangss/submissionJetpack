package com.komangss.submissionjetpack.utils

import android.content.Context
import com.komangss.submissionjetpack.data.source.remote.response.MovieResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies() : List<MovieResponse> {
        val movieResponseList = ArrayList<MovieResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("MovieResponse.json").toString())
            val listArray = responseObject.getJSONArray("movies")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                movieResponseList.add(
                    MovieResponse(
                        movie.getInt("id"),
                        movie.getString("title"),
                        movie.getString("director"),
                        movie.getString("description"),
                        movie.getInt("image"),
                        movie.getString("date"),
                        movie.getString("rating")
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return movieResponseList
    }
}