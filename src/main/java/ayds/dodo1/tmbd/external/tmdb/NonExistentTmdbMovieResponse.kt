package ayds.dodo1.tmbd.external.tmdb

object NonExistentTmdbMovieResponse : TmdbMovieResponse() {
    const val IMAGE_NOT_FOUND = "https://farm5.staticflickr.com/4363/36346283311_1dec5bb2c2.jpg"
    override var plot = "No Results"
    override var imageUrl = IMAGE_NOT_FOUND
}