package com.example.flag_game.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.flag_game.data.DataUtil
import com.example.flag_game.states.GuessState

/*
* This view model is the central place where all the data flows and manipulated based on user actions
* The composable screens simply access this and show the data on screen*/
class GuessHintViewModel : ViewModel() {
    private var attempt = 0

    //screen state for failed and correct
    var state by mutableStateOf(GuessState())
        private set

    val randomFlag by mutableStateOf(DataUtil.getRandomCountry())

    var dashedString by mutableStateOf(DataUtil.getDashesForCountryName(randomFlag.name))
        private set

    var guessedChar by mutableStateOf("")
        private set

    var hideKeyboard by mutableStateOf(false)
        private set

    fun updateGuessedChar(char: String) {
        hideKeyboard = false
        //if user try to type more then 1 char then hide the keyboard and abort
        if (char.length > 1) {
            hideKeyboard = true
            return
        }
        guessedChar = char
        hideKeyboard = true
    }

    private fun updateDashedString(value: String) {
        dashedString = value
        if (value.contains("-").not()) {
            //if there is no dash remain then show correct by updating the state
            state = GuessState(guessedAll = true)
        }
    }

    fun revealName() {
        dashedString = randomFlag.name
    }

    //main game logic on submit click
    fun replaceDashesWithChar() {
        if (randomFlag.name.length != dashedString.length) {
            throw IllegalArgumentException("Name and dashes lengths must be equal")
        }

        if (guessedChar.length != 1) {
            return
        }

        var result = ""
        randomFlag.name.forEachIndexed { index, c ->
            val str = c.toString()


            result += if (str.lowercase() == guessedChar.lowercase() || str == " ")
                str
            else if (dashedString[index].toString() != "-")
                str
            else
                "-"
        }

        if (result == dashedString)
            attempt++

        if (attempt >= 3)
            state = state.copy(failed = true)

        updateDashedString(result)
        updateGuessedChar("")

    }
}