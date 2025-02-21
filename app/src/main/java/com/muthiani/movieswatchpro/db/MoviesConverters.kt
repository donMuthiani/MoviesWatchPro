package com.muthiani.movieswatchpro.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muthiani.movieswatchpro.models.BelongsToCollection
import com.muthiani.movieswatchpro.models.Genres
import com.muthiani.movieswatchpro.models.ProductionCompanies
import com.muthiani.movieswatchpro.models.ProductionCountries
import com.muthiani.movieswatchpro.models.SpokenLanguages

class BelongsToCollectionConverter {
    @TypeConverter
    fun fromBelongsToCollection(belongsToCollection: BelongsToCollection?): String? {
        return belongsToCollection?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toBelongsToCollection(belongsToCollectionString: String?): BelongsToCollection? {
        return belongsToCollectionString?.let { Gson().fromJson(it, BelongsToCollection::class.java) }
    }

    @TypeConverter
    fun fromGenresList(genresList: ArrayList<Genres>?): String? {
        return genresList?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toGenresList(genresListString: String?): ArrayList<Genres>? {
        return genresListString?.let {
            val listType = object : TypeToken<ArrayList<Genres>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromSpokenLanguagesList(spokenLanguagesList: ArrayList<SpokenLanguages>?): String? {
        return spokenLanguagesList?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toSpokenLanguagesList(spokenLanguagesListString: String?): ArrayList<SpokenLanguages>? {
        return spokenLanguagesListString?.let {
            val listType = object : TypeToken<ArrayList<SpokenLanguages>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromProductionCompanies(productionCompanies: ArrayList<ProductionCompanies>?): String? {
        return productionCompanies?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toProductionCompanies(productionCompanies: String?): ArrayList<ProductionCompanies>? {
        return productionCompanies?.let {
            val listType = object : TypeToken<ArrayList<ProductionCompanies>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromProductionCountries(productionCountries: ArrayList<ProductionCountries>?): String? {
        return productionCountries?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toProductionCountries(productionCountries: String?): ArrayList<ProductionCountries>? {
        return productionCountries?.let {
            val listType = object : TypeToken<ArrayList<ProductionCountries>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromStringList(stringList: ArrayList<String>?): String? {
        return stringList?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toStringList(stringListString: String?): ArrayList<String>? {
        return stringListString?.let {
            val listType = object : TypeToken<ArrayList<String>>() {}.type
            Gson().fromJson(it, listType)
        }
    }
}
