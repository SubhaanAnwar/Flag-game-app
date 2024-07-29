package com.example.flag_game.ui

sealed class Screen(val route: String) {
    data object GuessTheCountryScreen: Screen("guess_the_country")
    data object GuessHintsScreen: Screen("guess_hints")
    data object GuessTheFlagScreen: Screen("guess_the_flag")
    data object AdvanceLevelScreen: Screen("advance_level")
}