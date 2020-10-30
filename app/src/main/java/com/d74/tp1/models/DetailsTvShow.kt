package com.example.tp1.models

data class DetailsTvShow(
    val EpisodeCount: Int,
    val Genres: MutableList<Genre>,
    val Image: String,
    val Plot: String,
    val Roles: MutableList<Role>,
    val Seasons: MutableList<Season>,
    val Studio: Studio,
    val TVParentalGuideline: String,
    val Title: String,
    val TvShowId: Int,
    val Year: Int
)