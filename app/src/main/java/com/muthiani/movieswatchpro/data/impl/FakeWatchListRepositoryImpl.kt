package com.muthiani.movieswatchpro.data.impl

import com.muthiani.movieswatchpro.data.FakeWatchListRepository
import com.muthiani.movieswatchpro.data.Movie
import com.muthiani.movieswatchpro.data.Result
import javax.inject.Inject

class FakeWatchListRepositoryImpl
    @Inject
    constructor() : FakeWatchListRepository {
        private val watchlist =
            listOf(
                Movie(
                    id = 1,
                    title = "Dune",
                    description = "A noble family becomes embroiled in a war for control of the galaxy's most valuable asset while its heir becomes troubled by visions of a dark future.",
                    imageUrl = "https://m.media-amazon.com/images/M/MV5BN2FjNmEyNWMtYzM0ZS00NjIyLTg5YzYtYThlMGVjNzE1OGViXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg",
                    category = "SCI-FI",
                    releaseDate = "2021-09-22",
                    progress = "45",
                    promoImage = "",
                ),
                Movie(
                    id = 2,
                    title = "Arrival",
                    description = "A linguist works with the military to communicate with alien lifeforms after twelve mysterious spacecraft appear around the world.",
                    imageUrl = "https://m.media-amazon.com/images/M/MV5BMTExMzU0ODcxNDheQTJeQWpwZ15BbWU4MDE1OTI4MzAy._V1_.jpg",
                    category = "SCI-FI",
                    releaseDate = "2024-09-11",
                    progress = "22",
                    promoImage = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-R0rV4gCO8OdBpT7OZ5s7yHCne3nPE7RTNw&s",
                ),
                Movie(
                    id = 3,
                    title = "Interstellar",
                    description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                    imageUrl = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
                    category = "SCI-FI",
                    releaseDate = "2024-08-11",
                    progress = "76",
                    promoImage = "https://cdn.mos.cms.futurecdn.net/LVoJnXBbUH6xx9EkfgVnc5.jpg",
                ),
                Movie(
                    id = 3,
                    title = "Mad Max: Fury Road",
                    description = "In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler in search for her homeland with the aid of a group of female prisoners and a drifter named Max.",
                    imageUrl = "https://m.media-amazon.com/images/M/MV5BZDRkODJhOTgtOTc1OC00NTgzLTk4NjItNDgxZDY4YjlmNDY2XkEyXkFqcGc@._V1_QL75_UX190_CR0,0,190,281_.jpg",
                    category = "ACTION",
                    releaseDate = "2024-10-07",
                    progress = "87",
                    promoImage = "https://static1.srcdn.com/wordpress/wp-content/uploads/2021/08/mad-max-fury-road-max-furiosa-poster.jpg",
                ),
                Movie(
                    id = 4,
                    title = "Parasite",
                    description = "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
                    imageUrl = "https://m.media-amazon.com/images/M/MV5BYWZjMjk3ZTItODQ2ZC00NTY5LWE0ZDYtZTI3MjcwN2Q5NTVkXkEyXkFqcGdeQXVyODk4OTc3MTY@._V1_FMjpg_UX1000_.jpg",
                    category = "THRILLER",
                    releaseDate = "2024-09-06",
                    progress = "50",
                    promoImage = "https://blogs.iu.edu/establishingshot/files/2019/09/Parasite-Header.jpg",
                ),
                Movie(
                    id = 5,
                    title = "1917",
                    description = "April 6th, 1917. As a regiment assembles to wage war deep in enemy territory, two soldiers are assigned to race against time and deliver a message that will stop 1,600 men from walking straight into a deadly trap.",
                    imageUrl = "https://m.media-amazon.com/images/I/81HBYgqw8lL._AC_UF894,1000_QL80_.jpg",
                    category = "WAR",
                    releaseDate = "2024-10-04",
                    progress = "90",
                    promoImage = "https://www.maxim.com/wp-content/uploads/2021/05/1917-promo.jpg?w=788&h=444&crop=1",
                ),
                Movie(
                    id = 6,
                    title = "The Grand Budapest Hotel",
                    description = "The adventures of Gustave H, a legendary concierge at a famous hotel from the fictional Republic of Zubrowka between the first and second World Wars, and Zero Moustafa, the lobby boy who becomes his most trusted friend.",
                    imageUrl = "https://m.media-amazon.com/images/M/MV5BMzM5NjUxOTEyMl5BMl5BanBnXkFtZTgwNjEyMDM0MDE@._V1_.jpg",
                    category = "COMEDY",
                    releaseDate = "2024-07-04",
                    progress = "5",
                    promoImage = "https://www.tallengestore.com/cdn/shop/products/GrandBudapestHotel-WesAnderson-TallengeHollywoodMoviePosterCollection_42d0d93c-ed2c-4aea-bab4-38262a332d4f.jpg?v=1602760296",
                ),
                Movie(
                    id = 7,
                    title = "Spider-Man: Into the Spider-Verse",
                    description = "Teen Miles Morales becomes Spider-Man of his reality, crossing his path with five counterparts from other dimensions to stop a threat for all realities.",
                    imageUrl = "https://m.media-amazon.com/images/M/MV5BMjMwNDkxMTgzOF5BMl5BanBnXkFtZTgwNTkwNTQ3NjM@._V1_FMjpg_UX1000_.jpg",
                    category = "ANIMATION",
                    releaseDate = "2024-09-04",
                    progress = "24",
                    promoImage = "https://sm.ign.com/t/ign_in/feature/s/spider-man/spider-man-across-the-spider-verse-exclusive-clip-features-s_wz6a.1280.jpg",
                ),
                Movie(
                    id = 8,
                    title = "Portrait of a Lady on Fire",
                    description = "On an isolated island in Brittany at the end of the eighteenth century, a female painter is obliged to paint a wedding portrait ofa young woman.",
                    imageUrl = "https://cdn.myportfolio.com/11a10d0ecd2796546b100d172d54cd37/bce30f56-951f-4957-a7a0-74ee3d0b2719_rw_1920.png?h=b274e7ba84e0a7558bbb61f2fe54f460",
                    category = "DRAMA",
                    releaseDate = "2024-10-08",
                    progress = "30",
                    promoImage = "https://i0.wp.com/www.franglais27tales.com/wp-content/uploads/2020/02/Portrait-of-a-Lady-on-Fire_sea_3.jpg_cmyk.jpg?resize=1972%2C1040&ssl=1",
                ),
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
