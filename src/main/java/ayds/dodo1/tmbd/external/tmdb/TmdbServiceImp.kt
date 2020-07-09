package ayds.dodo1.tmbd.external.tmdb

import ayds.dodo1.tmbd.external.TmdbService

class TmdbServiceImp(
        private val tmdbAPI: TheMovieDBAPI,
        private val tmdbMovieResolver: TmdbResponseToTmdbMovie
) : TmdbService {

    override fun getMovie(movie: String, year: String): TmdbMovieResponse {
        val callResponse = getTmdbMovieFromService(movie)
        return tmdbMovieResolver.getMovie(movie, year, callResponse?.body())
    }

    private fun getTmdbMovieFromService(title: String) = tmdbAPI.getTerm(title)?.execute()
}