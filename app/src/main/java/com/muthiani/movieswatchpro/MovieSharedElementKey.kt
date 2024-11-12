

package com.muthiani.movieswatchpro

data class MovieSharedElementKey(
    val snackId: Long,
    val type: MovieSharedElementType,
)

enum class MovieSharedElementType {
    Bounds,
    Image,
    Title,
    Tagline,
    Background,
}

object FilterMovieElementKey
