package com.muthiani.movieswatchpro.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muthiani.movieswatchpro.domain.entity.BelongsToCollection
import com.muthiani.movieswatchpro.domain.entity.Genres
import com.muthiani.movieswatchpro.domain.entity.ProductionCompanies
import com.muthiani.movieswatchpro.domain.entity.ProductionCountries
import com.muthiani.movieswatchpro.domain.entity.SpokenLanguages

class MoviesConverters {
    @TypeConverter
    fun fromBelongsToCollection(belongsToCollection: BelongsToCollection?): String? {
        return belongsToCollection?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toBelongsToCollection(belongsToCollectionString: String?): BelongsToCollection? {
        return belongsToCollectionString?.let { Gson().fromJson(it, BelongsToCollection::class.java) }
    }

    @TypeConverter
    fun fromGenresIds(genresList: List<Int>?): String? {
        return genresList?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toGenresIds(genresListString: String?): List<Int>? {
        return genresListString?.let {
            val listType = object : TypeToken<List<Int>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromGenresList(genresList: List<Genres>?): String? {
        return genresList?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toGenresList(genresListString: String?): List<Genres>? {
        return genresListString?.let {
            val listType = object : TypeToken<List<Genres>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromSpokenLanguagesList(spokenLanguagesList: List<SpokenLanguages>?): String? {
        return spokenLanguagesList?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toSpokenLanguagesList(spokenLanguagesListString: String?): List<SpokenLanguages>? {
        return spokenLanguagesListString?.let {
            val listType = object : TypeToken<List<SpokenLanguages>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromProductionCompanies(productionCompanies: List<ProductionCompanies>?): String? {
        return productionCompanies?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toProductionCompanies(productionCompanies: String?): List<ProductionCompanies>? {
        return productionCompanies?.let {
            val listType = object : TypeToken<List<ProductionCompanies>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromProductionCountries(productionCountries: List<ProductionCountries>?): String? {
        return productionCountries?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toProductionCountries(productionCountries: String?): List<ProductionCountries>? {
        return productionCountries?.let {
            val listType = object : TypeToken<List<ProductionCountries>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromStringList(stringList: List<String>?): String? {
        return stringList?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toStringList(stringListString: String?): List<String>? {
        return stringListString?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(it, listType)
        }
    }
}
