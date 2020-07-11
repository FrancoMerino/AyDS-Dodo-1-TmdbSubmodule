# AyDS-Dodo-1-TmdbSubmodule

Pasos para la comunicación con el servicio externo:
  1. Crear un objeto TmdbService, a través del método getInstance(), provisto por la misma interfaz.
  2. Llamar al método getMovie, suministrándole el título y año de la película
  3. Se obtendrá un objeto TmdbMovieResponse, conteniendo la información de la película encontrada en The Movie Database. En caso de no encontrar la película, el objeto será del tipo NonExistentTmdbMovieResponse.
