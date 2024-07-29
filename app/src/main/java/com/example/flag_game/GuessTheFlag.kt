package com.example.flag_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flag_game.data.DataUtil.countriesFlags
import com.example.flag_game.data.DataUtil.countriesName
import com.example.flag_game.ui.theme.Flag_gameTheme
import java.util.Random

class GuessTheFlag : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Guess_the_flag()
        }
    }
}

@Preview
@Composable
fun Guess_the_flag() {
    var randomFlag by remember { mutableStateOf(RandomFunciton()) }  //selects random flag
    var correctFlagIndex by remember { mutableStateOf(randomFlag[0]) } //flag indexing
    var FlagName by remember(correctFlagIndex) { mutableStateOf(countriesName[correctFlagIndex]) } //flags name
    var currentFlagIndex by remember { mutableStateOf(-1) }//the indexing for where the current flag is
    var result by remember { mutableStateOf("") } //final result that is shown

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Guess the Flag",
            style = TextStyle(fontSize = 30.sp),
            modifier = Modifier.padding(horizontal = 70.dp)
        )

        Text(
            text = "Country Name: $FlagName",  //Displays the flag name
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(horizontal = 70.dp, vertical = 10.dp)
        )

        val Flagchange = randomFlag.shuffled(Random(System.currentTimeMillis()))  //random flag

        for (i in Flagchange.indices) {
            val flagIndex = Flagchange[i]           //Indexing for the flags
            val flagDrawable = painterResource(id = countriesFlags[flagIndex])
            Image(
                painter = flagDrawable,      //picture of the flags
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .size(width = 150.dp, height = 150.dp)     //layout
                    .clickable {
                        if (flagIndex == correctFlagIndex && currentFlagIndex == -1) {
                            result = "CORRECT!"
                        } else if (currentFlagIndex == -1) {  //Displays whether the user is correct or incorrect
                            result = "INCORRECT!"             //Depending if the conditions are met
                        }
                        currentFlagIndex = flagIndex
                    }
            )
        }

        if (result == "CORRECT!") {
            Text(
                text = result,
                color = Color.Green,
                fontSize = 25.sp,
                modifier = Modifier.padding(horizontal = 70.dp, vertical = 10.dp)
            )
        }
        else{
            Text(
                text=result,
                color = Color.Red,
                fontSize = 25.sp,
                modifier = Modifier.padding(horizontal = 70.dp, vertical = 10.dp)    //layout
            )
        }

        if (currentFlagIndex != -1) {
            Text(
                text = "Next",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 70.dp, vertical = 10.dp)    //If the condition is met hen the button next will be displayed
                    .clickable {
                        randomFlag = RandomFunciton()
                        correctFlagIndex = randomFlag[0]
                        FlagName = countriesName[correctFlagIndex]
                        currentFlagIndex = -1
                        result = ""
                    }
            )
        }
    }
}

fun RandomFunciton(): List<Int> {
    val random = Random(System.currentTimeMillis())
    val correctFlagIndex = random.nextInt(countriesName.size)     //All the random functions for different variables val or val
    val otherIndices = (0 until countriesName.size).filter { it != correctFlagIndex }
    return otherIndices.shuffled(random).take(2) + correctFlagIndex
}