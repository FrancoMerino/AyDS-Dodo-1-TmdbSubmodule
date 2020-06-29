package ayds.dodo1.tmbd.external.tmdb

import ayds.dodo1.tmbd.external.ExternalService
import retrofit2.Response

internal class TmdbService(
        private val tmdbAPI: TheMovieDBAPI,
        private val tmdbMovieResolver: TmdbResponseToTmdbMovie
) : ExternalService{

    override fun getMovie(movie: String, year: Int): TmdbMovieResponse {
        val callResponse = getTmdbMovieFromService(movie)
        return tmdbMovieResolver.getMovie(movie, year, callResponse?.body())
    }

    private fun getTmdbMovieFromService(title: String) = tmdbAPI.getTerm(title)?.execute()
}