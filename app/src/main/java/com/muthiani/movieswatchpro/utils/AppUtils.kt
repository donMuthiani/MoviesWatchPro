package com.muthiani.movieswatchpro.utils

import java.time.LocalDate

fun String.isMovieRunning(): String {
    val localDate = LocalDate.parse(this)
    val oneMonthAgo = LocalDate.now().minusMonths(1)
    return if (localDate.isBefore(oneMonthAgo)) "Running" else "Not running"
}
