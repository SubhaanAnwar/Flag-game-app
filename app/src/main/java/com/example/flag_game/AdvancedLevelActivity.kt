package com.example.flag_game

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.flag_game.data.DataUtil
import com.example.flag_game.data.DataUtil.countriesFlags
import com.example.flag_game.data.DataUtil.countriesName
import com.example.flag_game.viewmodel.AdvanceLevelViewModel
import java.util.Random

class AdvancedLevelActivity : ComponentActivity() {
    private lateinit var viewModel: AdvanceLevelViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AdvanceLevelViewModel::class.java]
        setContent {
            AdvancedLevelsScreen(viewModel) {
                //when the next button clicked it will restart the activity
                finish()
                startActivity(Intent(this, AdvancedLevelActivity::class.java))
            }
        }
    }
}

@Composable
fun AdvancedLevelsScreen(viewModel: AdvanceLevelViewModel, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Advanced Level",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Score: ${viewModel.score}",                   //Scoring system
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.End)
        )
        CountryRow(
            flag = viewModel.firstCountry.flag,
            inputText = viewModel.firstCountryText,                 //flags displayed
            isError = viewModel.firstCountryError,
            isEnabled = viewModel.firstCountrySolved.not(),
            onTextUpdate = viewModel::updateFirstCountryText
        )

        CountryRow(
            flag = viewModel.secondCountry.flag,
            inputText = viewModel.secondCountryText,
            isError = viewModel.secondCountryError,                 //flags displayed
            isEnabled = viewModel.secondCountrySolved.not(),
            onTextUpdate = viewModel::updateSecondCountryText
        )

        CountryRow(
            flag = viewModel.thirdCountry.flag,
            inputText = viewModel.thirdCountryText,                      //flags displayed
            isError = viewModel.thirdCountryError,
            isEnabled = viewModel.thirdCountrySolved.not(),
            onTextUpdate = viewModel::updateThirdCountryText
        )

        if (viewModel.state.guessedAll) {
            Text(
                text = "CORRECT!",                   //output if correct or incorrect depending on the users input
                color = Color.Green
            )
        }

        if (viewModel.state.failed) {
            viewModel.revealNames()
            Text(
                text = "WRONG!",
                color = Color.Red
            )
        }

        Button(
            onClick = {
                if (viewModel.state.guessedAll || viewModel.state.failed) {
                    //update score
                    updateScores(
                        viewModel.firstCountrySolved,
                        viewModel.secondCountrySolved,                         //button to click for next and submit once a certain condition is met
                        viewModel.thirdCountrySolved
                    )
                    onNextClick()
                } else
                    viewModel.submit()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (viewModel.state.guessedAll || viewModel.state.failed) "Next" else "Submit")
        }
    }


}

/*A reusable UI component to show the flag and text box row*/
@Composable
fun CountryRow(
    @DrawableRes flag: Int,
    inputText: String,
    isError: Boolean,
    isEnabled: Boolean,
    onTextUpdate: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = flag), contentDescription = null,     //importing the images so it is displayed
            modifier = Modifier
                .sizeIn(150.dp)
                .weight(0.3f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        TextField(
            modifier = Modifier.weight(0.6f),
            value = inputText, onValueChange = onTextUpdate,
            isError = isError,
            enabled = isEnabled,
            placeholder = { Text(text = "Enter Country Name") }
        )

    }

}








/*This method will update score for every boolean value is true
it will take multiple boolean arguments*/
private fun updateScores(vararg args: Boolean) {
    for (result in args) {
        if (result) {
            DataUtil.updateScore()
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    AdvancedLevelsScreen(viewModel = AdvanceLevelViewModel()) {}
}



