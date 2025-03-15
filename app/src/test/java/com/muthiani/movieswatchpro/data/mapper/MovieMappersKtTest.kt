package com.muthiani.movieswatchpro.data.mapper

import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MovieEntityWatchList
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import org.junit.Test

class MovieMappersKtTest {
    @Test
    fun `test MovieModel toMovieEntity works correctly`() {
//        Given
        val movieModel =
            MovieModel(
                id = 1,
                adult = false,
                backdropPath = "/backdrop.jpg",
                genreIds = listOf(1, 2, 3),
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "Overview",
                popularity = 10.0,
                posterPath = "/poster.jpg",
                releaseDate = "2023-01-01",
                title = "Title",
                video = false,
                voteAverage = 8.0,
                voteCount = 100,
            )

//        when
        val movieEntity = movieModel.toMovieEntity()

//        then
        assert(movieEntity.id == movieModel.id)
        assert(movieEntity.adult == movieModel.adult)
        assert(movieEntity.backdropPath == movieModel.backdropPath)
        assert(movieEntity.genreIds == movieModel.genreIds)
        assert(movieEntity.originalLanguage == movieModel.originalLanguage)
        assert(movieEntity.originalTitle == movieModel.originalTitle)
        assert(movieEntity.overview == movieModel.overview)
        assert(movieEntity.popularity == movieModel.popularity)
        assert(movieEntity.posterPath == movieModel.posterPath)
        assert(movieEntity.releaseDate == movieModel.releaseDate)
        assert(movieEntity.title == movieModel.title)
        assert(movieEntity.video == movieModel.video)
        assert(movieEntity.voteAverage == movieModel.voteAverage)
        assert(movieEntity.voteCount == movieModel.voteCount)
    }

    @Test
    fun `test MovieEntity toMovieModel works correctly`() {
//        Given
        val movieEntity =
            MovieEntity(
                id = 1,
                adult = false,
                backdropPath = "/backdrop.jpg",
                genreIds = listOf(1, 2, 3),
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "Overview",
                popularity = 10.0,
                posterPath = "/poster.jpg",
                releaseDate = "2023-01-01",
                title = "Title",
                video = false,
                voteAverage = 8.0,
                voteCount = 100,
            )

//        When
        val movieModel = movieEntity.toMovieModel()

//        Then
        assert(movieModel.id == movieEntity.id)
        assert(movieModel.adult == movieEntity.adult)
        assert(movieModel.backdropPath == movieEntity.backdropPath)
        assert(movieModel.genreIds == movieEntity.genreIds)
        assert(movieModel.originalLanguage == movieEntity.originalLanguage)
        assert(movieModel.originalTitle == movieEntity.originalTitle)
        assert(movieModel.overview == movieEntity.overview)
        assert(movieModel.popularity == movieEntity.popularity)
        assert(movieModel.posterPath == movieEntity.posterPath)
        assert(movieModel.releaseDate == movieEntity.releaseDate)
        assert(movieModel.title == movieEntity.title)
        assert(movieModel.video == movieEntity.video)
        assert(movieModel.voteAverage == movieEntity.voteAverage)
        assert(movieModel.voteCount == movieEntity.voteCount)
    }

    @Test
    fun `test MovieModel toMovieEntityWatchList works correctly`()  {
        val movieModel =
            MovieModel(
                id = 1,
                adult = false,
                backdropPath = "/backdrop.jpg",
                genreIds = listOf(1, 2, 3),
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "Overview",
                popularity = 10.0,
                posterPath = "/poster.jpg",
                releaseDate = "2023-01-01",
                title = "Title",
                video = false,
                voteAverage = 8.0,
                voteCount = 100,
            )

        val movieEntityWatchList = movieModel.toMovieEntityWatchList()

        assert(movieEntityWatchList.id == movieModel.id)
        assert(movieEntityWatchList.adult == movieModel.adult)
        assert(movieEntityWatchList.backdropPath == movieModel.backdropPath)
        assert(movieEntityWatchList.genreIds == movieModel.genreIds)
        assert(movieEntityWatchList.originalLanguage == movieModel.originalLanguage)
        assert(movieEntityWatchList.originalTitle == movieModel.originalTitle)
        assert(movieEntityWatchList.overview == movieModel.overview)
        assert(movieEntityWatchList.popularity == movieModel.popularity)
        assert(movieEntityWatchList.posterPath == movieModel.posterPath)
        assert(movieEntityWatchList.releaseDate == movieModel.releaseDate)
        assert(movieEntityWatchList.title == movieModel.title)
        assert(movieEntityWatchList.video == movieModel.video)
        assert(movieEntityWatchList.voteAverage == movieModel.voteAverage)
        assert(movieEntityWatchList.voteCount == movieModel.voteCount)
    }

    @Test
    fun `test MovieModelWatchList toMovieModel works correctly`()  {
        val movieEntityWatchList =
            MovieEntityWatchList(
                id = 1,
                adult = false,
                backdropPath = "/backdrop.jpg",
                genreIds = listOf(1, 2, 3),
                originalLanguage = "en",
                originalTitle = "Original Title",
                overview = "Overview",
                popularity = 10.0,
                posterPath = "/poster.jpg",
                releaseDate = "2023-01-01",
                title = "Title",
                video = false,
                voteAverage = 8.0,
                voteCount = 100,
            )

        val movieModel = movieEntityWatchList.toMovieModel()

        assert(movieModel.id == movieEntityWatchList.id)
        assert(movieModel.adult == movieEntityWatchList.adult)
        assert(movieModel.backdropPath == movieEntityWatchList.backdropPath)
        assert(movieModel.genreIds == movieEntityWatchList.genreIds)
        assert(movieModel.originalLanguage == movieEntityWatchList.originalLanguage)
        assert(movieModel.originalTitle == movieEntityWatchList.originalTitle)
        assert(movieModel.overview == movieEntityWatchList.overview)
        assert(movieModel.popularity == movieEntityWatchList.popularity)
        assert(movieModel.posterPath == movieEntityWatchList.posterPath)
        assert(movieModel.releaseDate == movieEntityWatchList.releaseDate)
        assert(movieModel.title == movieEntityWatchList.title)
        assert(movieModel.video == movieEntityWatchList.video)
        assert(movieModel.voteAverage == movieEntityWatchList.voteAverage)
    }
}
