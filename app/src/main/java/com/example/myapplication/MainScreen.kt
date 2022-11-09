package com.example.myapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.Bluelight
import com.example.myapplication.ui.theme.Bluelight2

@Preview(showBackground = true)
@Composable
fun MainScreen() {

    val viewModel: MainViewModel = viewModel()

    val livesLeft = viewModel.lifeLeft.observeAsState().value.toString()
    val displayCorrectGuess = viewModel.displayCorrectGuess.observeAsState().value
    val displayIncorrectGuess = viewModel.incorrectGuess.observeAsState().value

    var guessLetter by remember {
        mutableStateOf("")
    }

    val gameOver = viewModel.gameOver.observeAsState().value
    val finalMassage = viewModel.setMassage()




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = displayCorrectGuess.toString())

        TextField(
            value = guessLetter,
            onValueChange = {
                guessLetter = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Go
            ), keyboardActions = KeyboardActions(
                onGo = {
                    viewModel.makeGuess(guessLetter.uppercase())

                    guessLetter = ""

                }
            ), shape = RoundedCornerShape(15.dp), colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Bluelight2, focusedIndicatorColor = Bluelight
            )
        )

        Text(text = "your false guess:$displayIncorrectGuess")

        Text(text = "your chance to guess is : $livesLeft")

        Button(onClick = {

            viewModel.makeGuess(guessLetter.uppercase())

            guessLetter = ""

        }) {
            Text(text = "make guess")
        }

        AnimatedVisibility(visible = gameOver!!) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = finalMassage)

                Button(onClick = { viewModel.newGame() }) {

                    Text(text = "New Game")

                }

            }



        }

    }


}