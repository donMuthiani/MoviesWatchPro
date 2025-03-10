package com.muthiani.movieswatchpro.domain.entity

import androidx.paging.compose.LazyPagingItems
import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
)

data class Genres(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
)

data class ProductionCompanies(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("logo_path") var logoPath: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("origin_country") var originCountry: String? = null,
)

data class ProductionCountries(
    @SerializedName("iso_3166_1") var iso31661: String? = null,
    @SerializedName("name") var name: String? = null,
)

data class MovieModel(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("genre_ids") var genreIds: List<Int> = listOf(),
    @SerializedName("id") var id: Int,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null,
)

data class SpokenLanguages(
    @SerializedName("english_name") var englishName: String? = null,
    @SerializedName("iso_639_1") var iso6391: String? = null,
    @SerializedName("name") var name: String? = null,
)

data class MovieCollection(
    val name: String,
    val movies: LazyPagingItems<MovieModel>,
    val onMovieClicked: (Long) -> Unit = {},
)
