package com.muthiani.movieswatchpro.models

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
data class Onboarding(val isLoggedIn: Boolean = false)

@Serializable
object Home
