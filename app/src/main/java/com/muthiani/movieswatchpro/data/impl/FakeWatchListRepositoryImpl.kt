package com.muthiani.movieswatchpro.data.impl

import com.muthiani.movieswatchpro.data.FakeWatchListRepository
import com.muthiani.movieswatchpro.data.Movie
import com.muthiani.movieswatchpro.data.Result
import javax.inject.Inject

class FakeWatchListRepositoryImpl @Inject constructor() : FakeWatchListRepository {
    private val watchlist = listOf(
        Movie(
            id = 1,
            title = "Dune",
            description = "A noble family becomes embroiled in a war for control of the galaxy's most valuable asset while its heir becomes troubled by visions of a dark future.",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BN2FjNmEyNWMtYzM0ZS00NjIyLTg5YzYtYThlMGVjNzE1OGViXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg",
            category = "SCI-FI",
            releaseDate = "September 2021",
            progress = "45"
        ),
        Movie(
            id = 2,
            title = "Arrival",
            description = "A linguist works with the military to communicate with alien lifeforms after twelve mysterious spacecraft appear around the world.",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BMTExMzU0ODcxNDheQTJeQWpwZ15BbWU4MDE1OTI4MzAy._V1_.jpg",
            category = "SCI-FI",
            releaseDate = "November 2016",
            progress = "22"
        ),
        Movie(
            id = 3,
            title = "Interstellar",
            description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
            category = "SCI-FI",
            releaseDate = "November 2014",
            progress = "76"
        ),
        Movie(
            id = 3,
            title = "Mad Max: Fury Road",
            description = "In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler in search for her homeland with the aid of a group of female prisoners and a drifter named Max.",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BZDRkODJhOTgtOTc1OC00NTgzLTk4NjItNDgxZDY4YjlmNDY2XkEyXkFqcGc@._V1_QL75_UX190_CR0,0,190,281_.jpg",
            category = "ACTION",
            releaseDate = "May 2015",
            progress = "87"
        ),
        Movie(
            id = 4,
            title = "Parasite",
            description = "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BYWZjMjk3ZTItODQ2ZC00NTY5LWE0ZDYtZTI3MjcwN2Q5NTVkXkEyXkFqcGdeQXVyODk4OTc3MTY@._V1_FMjpg_UX1000_.jpg",
            category = "THRILLER",
            releaseDate = "October 2019",
            progress = "50"
        ),
        Movie(
            id = 5,
            title = "1917",
            description = "April 6th, 1917. As a regiment assembles to wage war deep in enemy territory, two soldiers are assigned to race against time and deliver a message that will stop 1,600 men from walking straight into a deadly trap.",
            imageUrl = "https://m.media-amazon.com/images/I/81HBYgqw8lL._AC_UF894,1000_QL80_.jpg",
            category = "WAR",
            releaseDate = "December 2019",
            progress = "90"
        ),
        Movie(
            id = 6,
            title = "The Grand Budapest Hotel",
            description = "The adventures of Gustave H, a legendary concierge at a famous hotel from the fictional Republic of Zubrowka between the first and second World Wars, and Zero Moustafa, the lobby boy who becomes his most trusted friend.",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BMzM5NjUxOTEyMl5BMl5BanBnXkFtZTgwNjEyMDM0MDE@._V1_.jpg",
            category = "COMEDY",
            releaseDate = "February 2014",
            progress = "5"
        ),
        Movie(
            id = 7,
            title = "Spider-Man: Into the Spider-Verse",
            description = "Teen Miles Morales becomes Spider-Man of his reality, crossing his path with five counterparts from other dimensions to stop a threat for all realities.",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BMjMwNDkxMTgzOF5BMl5BanBnXkFtZTgwNTkwNTQ3NjM@._V1_FMjpg_UX1000_.jpg",
            category = "ANIMATION",
            releaseDate = "December 2018",
            progress = "24"
        ),
        Movie(
            id = 8,
            title = "Portrait of a Lady on Fire",
            description = "On an isolated island in Brittany at the end of the eighteenth century, a female painter is obliged to paint a wedding portrait ofa young woman.",
            imageUrl = "https://cdn.myportfolio.com/11a10d0ecd2796546b100d172d54cd37/bce30f56-951f-4957-a7a0-74ee3d0b2719_rw_1920.png?h=b274e7ba84e0a7558bbb61f2fe54f460",
            category = "DRAMA",
            releaseDate = "September 2019",
            progress = "30"
        )
    )

    override suspend fun getWatchList(): Result<List<Movie>> {
        return Result.Success(watchlist)
    }

    override suspend fun getMyShows(): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovie(movieId: Int): Movie? {
        return watchlist.firstOrNull { it.id == movieId }
    }
}
