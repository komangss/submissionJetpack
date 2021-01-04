package com.komangss.submissionjetpack.utils

import android.content.Context
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
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

    fun loadMovies(): List<MovieResponse> {
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
                        movie.getString("image"),
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

    fun loadTvShow(): List<TvShowResponse> {
        val tvShowResponseList = ArrayList<TvShowResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("TvShowResponse.json").toString())
            val listArray = responseObject.getJSONArray("tvShows")
            for (i in 0 until listArray.length()) {
                val tvShow = listArray.getJSONObject(i)

                tvShowResponseList.add(
                    TvShowResponse(
                        tvShow.getInt("id"),
                        tvShow.getString("title"),
                        tvShow.getString("description"),
                        tvShow.getString("image"),
                        tvShow.getString("releaseDate"),
                        tvShow.getString("rating")
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return tvShowResponseList
    }

    fun loadMovieById(id: Int): MovieResponse? {
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
                        movie.getString("image"),
                        movie.getString("date"),
                        movie.getString("rating")
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return movieResponseList.firstOrNull { it.id == id }
    }

    fun loadTvShowById(id: Int): TvShowResponse? {
        val tvShowResponseList = ArrayList<TvShowResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("TvShowResponse.json").toString())
            val listArray = responseObject.getJSONArray("tvShows")
            for (i in 0 until listArray.length()) {
                val tvShow = listArray.getJSONObject(i)

                tvShowResponseList.add(
                    TvShowResponse(
                        tvShow.getInt("id"),
                        tvShow.getString("title"),
                        tvShow.getString("description"),
                        tvShow.getString("image"),
                        tvShow.getString("releaseDate"),
                        tvShow.getString("rating")
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return tvShowResponseList.firstOrNull { it.id == id }
    }
}