package com.muthiani.movieswatchpro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.muthiani.movieswatchpro.domain.entity.BelongsToCollection
import com.muthiani.movieswatchpro.domain.entity.Genres
import com.muthiani.movieswatchpro.domain.entity.ProductionCompanies
import com.muthiani.movieswatchpro.domain.entity.ProductionCountries
import com.muthiani.movieswatchpro.domain.entity.SpokenLanguages

@TypeConverters
@Entity(tableName = "watchlist")
data class MovieEntityWatchList(
    @PrimaryKey
    var id: Int,
    var adult: Boolean? = null,
    var backdropPath: String? = null,
    var genreIds: List<Int> = listOf(),
    var originalLanguage: String? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var popularity: Double? = null,
    var posterPath: String? = null,
    var releaseDate: String? = null,
    var title: String? = null,
    var video: Boolean? = null,
    var voteAverage: Double? = null,
    var voteCount: Int? = null
    )
