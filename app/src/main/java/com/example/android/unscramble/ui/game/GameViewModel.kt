package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel


class GameViewModel: ViewModel() {
    private var _score = 0
    val score:Int
        get()=_score

    private var _currentWordCount = 0
    val currentWordCount:Int
        get()=_currentWordCount

    private lateinit var _currentScrambleWord: String
    val currentScrambledWord: String
        get() = _currentScrambleWord

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord:String


    // Declare private mutable variable that can only be modified
// within the class it is declared.
    private var _count = 0

    // Declare another public immutable field and override its getter method.
// Return the private property's value in the getter method.
// When count is accessed, the get() function is called and
// the value of _count is returned.
    val count: Int
        get() = _count

    private fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambleWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }
    fun nextWord():Boolean{
        return if (_currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else false
    }
    private fun increaseScore(){
        _score += SCORE_INCREASE
    }
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }
    /*
* Re-initializes the game data to restart the game.
*/
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
}
