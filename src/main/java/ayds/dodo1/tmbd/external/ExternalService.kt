package ayds.dodo1.tmbd.external

import ayds.dodo1.tmbd.external.tmdb.TmdbMovieResponse


interface ExternalService {
    fun getMovie(movie : String, year: String) : TmdbMovieResponse
}