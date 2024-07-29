package com.example.flag_game.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.flag_game.data.DataUtil
import com.example.flag_game.data.DataUtil.getRandomCountry
import com.example.flag_game.states.AdvanceState

/*
* This view model is the central place where all the data flows and manipulated based on user actions
* The composable screens simply access this and show the data on screen*/
class AdvanceLevelViewModel : ViewModel() {
    private var attempts = 0

    //screen state for failed and correct
    var state by mutableStateOf(AdvanceState())
        private set

    //random countries
    val firstCountry by mutableStateOf(getRandomCountry())
    val secondCountry by mutableStateOf(getRandomCountry())
    val thirdCountry by mutableStateOf(getRandomCountry())

    //error booleans on submit
    var firstCountryError by mutableStateOf(false)
        private set

    var secondCountryError by mutableStateOf(false)
        private set

    var thirdCountryError by mutableStateOf(false)
        private set

    //correct guesses boolean
    var firstCountrySolved by mutableStateOf(false)
        private set

    var secondCountrySolved by mutableStateOf(false)
        private set

    var thirdCountrySolved by mutableStateOf(false)
        private set

    //user input texts
    var firstCountryText by mutableStateOf("")
        private set

    var secondCountryText by mutableStateOf("")
        private set

    var thirdCountryText by mutableStateOf("")
        private set

    var score by mutableIntStateOf(DataUtil.getScore())

    //methods to manipulate data based on user action
    fun updateFirstCountryText(value: String) {
        firstCountryText = value
    }

    fun updateSecondCountryText(value: String) {
        secondCountryText = value
    }

    fun updateThirdCountryText(value: String) {
        thirdCountryText = value
    }

    fun submit() {
        attempts++
        if (attempts >= 3) {
            state = state.copy(failed = true)
            return
        }

        if (firstCountryText.lowercase() == firstCountry.name.lowercase())
            firstCountrySolved = true
        else
            firstCountryError = true

        if (secondCountryText.lowercase() == secondCountry.name.lowercase())
            secondCountrySolved = true
        else
            secondCountryError = true

        if (thirdCountryText.lowercase() == thirdCountry.name.lowercase())
            thirdCountrySolved = true
        else
            thirdCountryError = true

        state =
            state.copy(guessedAll = firstCountrySolved && secondCountrySolved && thirdCountrySolved)
    }

    fun revealNames() {
        firstCountryText = firstCountry.name
        secondCountryText = secondCountry.name
        thirdCountryText = thirdCountry.name
    }

}
