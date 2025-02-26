package com.muthiani.movieswatchpro.presentation.utils

import java.time.LocalDate

fun String.isMovieRunning(): String {
    val localDate = LocalDate.parse(this)
    val oneMonthAgo = LocalDate.now().minusMonths(1)
    return if (localDate.isBefore(oneMonthAgo)) "Running" else "Not running"
}

fun String.toCamelCase(): String {
    return this
        .trim() // Remove leading/trailing spaces
        .lowercase() // Convert everything to lowercase first
        .split("_") // Split by underscores
        .filter { it.isNotEmpty() } // Remove empty parts
        .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
}
