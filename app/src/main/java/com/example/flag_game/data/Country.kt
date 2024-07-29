package com.example.flag_game.data

import androidx.annotation.DrawableRes

// wrapper class to store name and flag together//
data class Country(
    val name: String,
    @DrawableRes
    val flag: Int,
)
