package ayds.dodo1.tmbd.external

import ayds.dodo1.tmbd.external.tmdb.TheMovieDBAPI
import ayds.dodo1.tmbd.external.tmdb.TmdbMovieResponse
import ayds.dodo1.tmbd.external.tmdb.TmdbResponseToTmdbMovieImp
import ayds.dodo1.tmbd.external.tmdb.TmdbServiceImp
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


interface TmdbService {
    companion object Factory {
        const val TMDB_URL_BASE = "https://api.themoviedb.org/3/"
        fun getInstance(): TmdbService {
            val retrofit = Retrofit.Builder().baseUrl(TMDB_URL_BASE).addConverterFactory(ScalarsConverterFactory.create()).build()
            val tmdbAPI = retrofit.create(TheMovieDBAPI::class.java)
            return TmdbServiceImp(tmdbAPI, TmdbResponseToTmdbMovieImp())
        }
    }
    fun getMovie(movie : String, year: String) : TmdbMovieResponse
}