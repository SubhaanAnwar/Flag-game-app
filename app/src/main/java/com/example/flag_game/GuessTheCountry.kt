package com.example.flag_game

import android.os.Bundle
import android.widget.ScrollView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flag_game.data.DataUtil.countriesFlags
import com.example.flag_game.data.DataUtil.countriesName
import com.example.flag_game.ui.theme.Flag_gameTheme
import java.util.Random
class GuessTheCountry : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessCountry()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun GuessCountry() {

    var guessedCorrectly by remember { mutableStateOf<Boolean?>(null) }  //Just uses boolean so false and true statements. Currently on nothing
    var expanded by remember { mutableStateOf(false) }


    var selected by remember { mutableStateOf(countriesName[0]) }  // The selected countrty
    val scrollState = rememberScrollState() // Used for the dropdownmenu


    var randomFlagIndex by remember { mutableStateOf(random.nextInt(countriesName.size)) } //indexing for he flag
    var flag1 by remember { mutableStateOf(countriesName[randomFlagIndex]) } //displays flag1 name
    val flagDrawable = painterResource(id = countriesFlags[randomFlagIndex]) //draws the flag from the data


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = flagDrawable,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 70.dp, vertical = 30.dp)  //layout
                .size(width = 250.dp, height = 250.dp)
        )

        Text(
            text = "Guess the country",
            style = TextStyle(fontSize = 30.sp),
            modifier = Modifier.padding(horizontal = 70.dp)
        )

        // Dropdown menu
        Box(
            modifier = Modifier
                .padding(horizontal = 70.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),   //Drop down menu for the user to select an option for the user to selec
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .menuAnchor(),
                    value = selected,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    countriesName.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text = text) },
                            onClick = {
                                selected = countriesName[index]
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }

        if (guessedCorrectly != null) {
            if (guessedCorrectly == true) {
                Text(
                    text = "CORRECT !",
                    color = Color.Green,
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            } else {
                Text(
                    text = "INCORRECT !",
                    color = Color.Red,
                    style = TextStyle(fontSize = 20.sp),    //conditions if the answer is correct or incorrect
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                Text(
                    text = "$flag1",
                    color = Color.Blue,
                    style = TextStyle(fontSize = 20.sp),    //flag name displayed when the user guesses wrong
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }



        fun NewCountry() {
            randomFlagIndex = random.nextInt(countriesName.size)
            flag1 = countriesName[randomFlagIndex]
            guessedCorrectly = null      //Selects a new counry to be displayed
            selected = countriesName[0]
            expanded = false



        }

        Button(
            onClick = {
                if (guessedCorrectly == null) {
                    if (selected == flag1) {
                        guessedCorrectly = true              //Depending on if the condition is met it will display a new button called next
                    } else {
                        guessedCorrectly = false
                    }
                } else {
                    NewCountry()
                }
            },
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            if (guessedCorrectly == null) {
                Text("Submit")
            } else {
                Text("Next")
            }
        }
    }
}
