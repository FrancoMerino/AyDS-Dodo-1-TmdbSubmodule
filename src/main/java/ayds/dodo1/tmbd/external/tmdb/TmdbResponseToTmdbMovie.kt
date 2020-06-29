package ayds.dodo1.tmbd.external.tmdb

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import java.io.IOException

interface TmdbResponseToTmdbMovie {
    fun getMovie(movie: String, year: Int, body: String?): TmdbMovieResponse
}

class TmdbResponseToTmdbMovieImp : TmdbResponseToTmdbMovie {
    companion object {
        private const val IMAGE_URL_BASE = "https://image.tmdb.org/t/p/w400/"
        private const val RESULTS_JSON = "results"
        private const val OVERVIEW_JSON = "overview"
        private const val POSTER_PATH_JSON = "poster_path"
        private const val BACKDROP_PATH_JSON = "backdrop_path"
        private const val RELEASE_DATE = "release_date"
    }

    override fun getMovie(movie: String, year: Int, body: String?): TmdbMovieResponse {
        try {
            val resultIterator: Iterator<JsonElement> = getJsonElementIterator(body) as Iterator<JsonElement>
            val result: JsonObject? = getInfoFromTmdb(resultIterator, year)
            if (result != null) {
                val backdropPath: String? = getBackdrop(result)
                val posterPath = result[POSTER_PATH_JSON]
                val extract = result[OVERVIEW_JSON]
                if (extract !== JsonNull.INSTANCE && posterPath !== JsonNull.INSTANCE) {
                    val path = if (backdropPath != null && backdropPath != "") IMAGE_URL_BASE + backdropPath else NonExistentTmdbMovieResponse.IMAGE_NOT_FOUND
                    return createTmdbMovie(movie, extract.asString, path, posterPath.asString)
                }
            }
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return NonExistentTmdbMovieResponse
    }

    private fun getBackdrop(result: JsonObject): String? {
        val backdropPathJson = result[BACKDROP_PATH_JSON]
        var backdropPath: String? = null
        if (!backdropPathJson.isJsonNull) {
            backdropPath = backdropPathJson.asString
        }
        return backdropPath
    }

    private fun getInfoFromTmdb(resultIterator: Iterator<JsonElement>, movieYear: Int): JsonObject? {
        var result: JsonObject? = null
        while (resultIterator.hasNext()) {
            result = resultIterator.next().asJsonObject
            val year = result[RELEASE_DATE].asString.split("-").toTypedArray()[0]
            if (year == movieYear.toString()) break
        }
        return result
    }

    @Throws(IOException::class)
    private fun getJsonElementIterator(body: String?): Iterator<JsonElement?> {
        val gson = Gson()
        val jobj = gson.fromJson(body, JsonObject::class.java)
        return jobj[RESULTS_JSON].asJsonArray.iterator()
    }

    private fun createTmdbMovie(movie: String, text: String, path: String, posterPath: String): TmdbMovieResponse {
        val tmdbMovie = TmdbMovieResponse()
        tmdbMovie.title = movie
        tmdbMovie.plot = text
        tmdbMovie.imageUrl = path
        tmdbMovie.posterUrl = posterPath
        return tmdbMovie
    }
}