package com.muthiani.movieswatchpro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.muthiani.movieswatchpro.domain.entity.BelongsToCollection
import com.muthiani.movieswatchpro.domain.entity.Genres
import com.muthiani.movieswatchpro.domain.entity.ProductionCompanies
import com.muthiani.movieswatchpro.domain.entity.ProductionCountries
import com.muthiani.movieswatchpro.domain.entity.SpokenLanguages

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    var id: Int,
    var adult: Boolean? = null,
    var backdropPath: String? = null,
    var belongsToCollection: BelongsToCollection? = BelongsToCollection(),
    var budget: Int? = null,
    var genres: ArrayList<Genres>? = arrayListOf(),
    var homepage: String? = null,
    var imdbId: String? = null,
    var originCountry: ArrayList<String>? = arrayListOf(),
    var originalLanguage: String? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var popularity: Double? = null,
    var posterPath: String? = null,
    var productionCompanies: ArrayList<ProductionCompanies>? = arrayListOf(),
    var productionCountries: ArrayList<ProductionCountries>? = arrayListOf(),
    var releaseDate: String? = null,
    var revenue: Int? = null,
    var runtime: Int? = null,
    var spokenLanguages: ArrayList<SpokenLanguages>? = arrayListOf(),
    var status: String? = null,
    var tagline: String? = null,
    var title: String? = null,
    var video: Boolean? = null,
    var voteAverage: Double? = null,
    var voteCount: Int? = null,
)
