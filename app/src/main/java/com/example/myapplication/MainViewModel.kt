package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val words = listOf("Android", "kotlin", "jetpack", "compose", "sina", "sona", "sarina")

    private var secretWord = words.random().uppercase()

    private val _displayCorrectGuess = MutableLiveData("")
    val displayCorrectGuess: LiveData<String> get() = _displayCorrectGuess

    private var correctGuess = ""

    private val _incorrectGuess = MutableLiveData("")
    val incorrectGuess: LiveData<String> get() = _incorrectGuess

    private val _gameOver = MutableLiveData(false)
    val gameOver: LiveData<Boolean> get() = _gameOver


    private val _lifeLeft = MutableLiveData(10)
    val lifeLeft: LiveData<Int> get() = _lifeLeft

    init {

        _displayCorrectGuess.value = driveSecretWord()
        secretWord = words.random().uppercase()
    }


    private fun decreaseLife() {
        _lifeLeft.postValue(lifeLeft.value?.dec())
    }


    fun makeGuess(guess: String) {

        if (guess.length == 1) {

            if (secretWord.contains(guess)) {

                correctGuess += guess

                _displayCorrectGuess.value = driveSecretWord()


            } else {

                _incorrectGuess.value += guess

                decreaseLife()


            }
        }
        if (isLost() || isWon()) {

            _gameOver.value = true

        }

    }

    private fun driveSecretWord(): String {

        var display = ""

        secretWord.forEach {

            display += checkLetter(it.toString())

        }

        return display

    }

    private fun checkLetter(str: String) = when (correctGuess.contains(str)) {
        true -> str
        false -> "_"
    }

    private fun isLost() = (lifeLeft.value ?: 0) <= 0
    private fun isWon() = displayCorrectGuess.value == secretWord

    fun setMassage(): String {

        var massage = ""

        if (isWon()) massage = "You Won ! \n Correct guess is $secretWord"
        if (isLost()) massage = "You Lost ! \n Correct guess is $secretWord"

        return massage


    }

    fun newGame() {

        secretWord = words.random().uppercase()

        correctGuess = ""
        _displayCorrectGuess.value = ""
        _incorrectGuess.value = ""

        _lifeLeft.value = 10


    }


}