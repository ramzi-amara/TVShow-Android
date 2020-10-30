package com.example.tp1.models

data class DetailsSeason(
    val SeasonId: Int,
    val EpisodeCount: Int,
    val Episodes: MutableList<Episode>
)