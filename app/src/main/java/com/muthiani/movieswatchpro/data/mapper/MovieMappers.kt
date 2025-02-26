package com.muthiani.movieswatchpro.data.mapper

import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.domain.entity.MovieModel

fun MovieModel.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id, // Int
        adult = this.adult, // Boolean?
        backdropPath = this.backdropPath, // String?
        belongsToCollection = this.belongsToCollection, // BelongsToCollection?
        budget = this.budget, // Int?
        genres = this.genres, // ArrayList<Genres>?
        homepage = this.homepage, // String?
        imdbId = this.imdbId, // String?
        originCountry = this.originCountry, // ArrayList<String>?
        originalLanguage = this.originalLanguage, // String?
        originalTitle = this.originalTitle, // String?
        overview = this.overview, // String?
        popularity = this.popularity, // Double?
        posterPath = this.posterPath, // String?
        productionCompanies = this.productionCompanies, // ArrayList<ProductionCompanies>?
        productionCountries = this.productionCountries, // ArrayList<ProductionCountries>?
        releaseDate = this.releaseDate, // String?
        revenue = this.revenue, // Int?
        runtime = this.runtime, // Int?
        spokenLanguages = this.spokenLanguages, // ArrayList<SpokenLanguages>?
        status = this.status, // String?
        tagline = this.tagline, // String?
        title = this.title, // String?
        video = this.video, // Boolean?
        voteAverage = this.voteAverage, // Double?
        voteCount = this.voteCount, // Int?
    )
}

fun MovieEntity.toMovieModel(): MovieModel {
    return MovieModel(
        id = this.id, // Int
        adult = this.adult, // Boolean?
        backdropPath = this.backdropPath, // String?
        belongsToCollection = this.belongsToCollection, // BelongsToCollection?
        budget = this.budget, // Int?
        genres = this.genres, // ArrayList<Genres>?
        homepage = this.homepage, // String?
        imdbId = this.imdbId, // String?
        originCountry = this.originCountry, // ArrayList<String>?
        originalLanguage = this.originalLanguage, // String?
        originalTitle = this.originalTitle, // String?
        overview = this.overview, // String?
        popularity = this.popularity, // Double?
        posterPath = this.posterPath, // String?
        productionCompanies = this.productionCompanies, // ArrayList<ProductionCompanies>?
        productionCountries = this.productionCountries, // ArrayList<ProductionCountries>?
        releaseDate = this.releaseDate, // String?
        revenue = this.revenue, // Int?
        runtime = this.runtime, // Int?
        spokenLanguages = this.spokenLanguages, // ArrayList<SpokenLanguages>?
        status = this.status, // String?
        tagline = this.tagline, // String?
        title = this.title, // String?
        video = this.video, // Boolean?
        voteAverage = this.voteAverage, // Double?
        voteCount = this.voteCount, // Int?
    )
}
