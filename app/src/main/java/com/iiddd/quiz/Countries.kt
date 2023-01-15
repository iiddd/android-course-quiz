package com.iiddd.quiz

import kotlinx.serialization.Serializable

@Serializable
data class Countries(
    val countries: MutableList<String>
)