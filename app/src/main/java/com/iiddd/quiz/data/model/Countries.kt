package com.iiddd.quiz.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Countries(
    val countries: MutableList<String>
)