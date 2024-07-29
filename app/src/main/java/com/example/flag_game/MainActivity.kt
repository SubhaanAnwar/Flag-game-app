//LINK TO VIDEO OF EXPLANATION
//https://youtu.be/6H_MyLMsYVU
package com.example.flag_game

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flag_game.data.DataUtil
import java.util.Random


val random = Random()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        //Reset the score when user finish playing the advance level
        DataUtil.resetScore()
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Text(
            stringResource(id = R.string.app_name),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val i = Intent(context, GuessTheCountry::class.java)
                context.startActivity(i)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guess the Country")
        }
        Button(
            onClick = {
                val i = Intent(context, GuessHintsActivity::class.java)   //All the buttons that redirect you to the specific screens
                context.startActivity(i)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guess hints")
        }
        Button(
            onClick = {
                val i = Intent(context, GuessTheFlag::class.java)
                context.startActivity(i)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guess the Flag")
        }
        Button(
            onClick = {
                val i = Intent(context, AdvancedLevelActivity::class.java)
                context.startActivity(i)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Advanced Level")
        }
    }
}


//REFRENCES
//https://www.youtube.com/watch?v=cDabx3SjuOY&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC
//https://developer.android.com/jetpack/compose
//https://medium.com/androiddevelopers/viewmodels-a-simple-example-ed5ac416317e
//https://api.flutter.dev/flutter/material/DropdownMenu-class.html
//https://youtu.be/6_wK_Ud8--0
//https://www.jetpackcompose.net/
//https://docs.logrocket.com/reference/jetpack-compose-about
//https://blog.sentry.io/getting-started-with-jetpack-compose/