package com.muthiani.movieswatchpro.presentation.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun String.isMovieRunning(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        val date = format.parse(this) ?: return "Not running"
        val calendar = Calendar.getInstance().apply { time = date }

        val oneMonthAgo =
            Calendar.getInstance().apply {
                add(Calendar.MONTH, -1)
            }

        if (calendar.before(oneMonthAgo)) "Running" else "Not running"
    } catch (e: Exception) {
        "Invalid date"
    }
}

fun String.toCamelCase(): String {
    return this
        .trim() // Remove leading/trailing spaces
        .lowercase() // Convert everything to lowercase first
        .split("_") // Split by underscores
        .filter { it.isNotEmpty() } // Remove empty parts
        .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
}
