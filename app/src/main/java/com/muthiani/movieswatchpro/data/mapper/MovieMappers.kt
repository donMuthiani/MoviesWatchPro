package com.muthiani.movieswatchpro.data.mapper

import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.domain.entity.MovieModel

fun MovieModel.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        belongsToCollection = this.belongsToCollection,
        budget = this.budget,
        genres = this.genres,
        homepage = this.homepage,
        imdbId = this.imdbId,
        originCountry = this.originCountry,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        productionCompanies = this.productionCompanies,
        productionCountries = this.productionCountries,
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        spokenLanguages = this.spokenLanguages,
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
    )
}

fun MovieEntity.toMovieModel(): MovieModel {
    return MovieModel(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        belongsToCollection = this.belongsToCollection,
        budget = this.budget,
        genres = this.genres,
        homepage = this.homepage,
        imdbId = this.imdbId,
        originCountry = this.originCountry,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        productionCompanies = this.productionCompanies,
        productionCountries = this.productionCountries,
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        spokenLanguages = this.spokenLanguages,
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
    )
}
