package com.example.flag_game

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.flag_game.viewmodel.GuessHintViewModel

class GuessHintsActivity : ComponentActivity() {
    private lateinit var viewModel: GuessHintViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[GuessHintViewModel::class.java]
        setContent {
            GuessHintsScreen(viewModel) {
                //Handle when next button click by restarting this activity
                finish()
                startActivity(Intent(this, GuessHintsActivity::class.java))
            }
        }
    }
}

@Composable
fun GuessHintsScreen(viewModel: GuessHintViewModel, onNextClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (viewModel.hideKeyboard) {
                keyboardController?.hide()
            }
            Text(
                text = "Guess Hints",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Image(
                painter = painterResource(id = viewModel.randomFlag.flag),  //random flag selected
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = viewModel.dashedString,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 8.sp,
                color = if (viewModel.state.failed) Color.Blue else Color.Unspecified
            )

            OutlinedTextField(
                value = viewModel.guessedChar,
                onValueChange = viewModel::updateGuessedChar,
                singleLine = true,
            )
            AnimatedVisibility(visible = viewModel.state.guessedAll) {
                Text(
                    text = "CORRECT!",
                    color = Color.Green
                )
            }

            if (viewModel.state.failed) {
                viewModel.revealName()
                Text(
                    text = "WRONG!",
                    color = Color.Red
                )
            }

        }
        Button(
            onClick = {
                if (viewModel.state.guessedAll || viewModel.state.failed) {
                    onNextClick()
                } else
                    viewModel.replaceDashesWithChar()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        ) {
            Text(text = if (viewModel.state.guessedAll || viewModel.state.failed) "Next" else "Submit")
        }
    }
}