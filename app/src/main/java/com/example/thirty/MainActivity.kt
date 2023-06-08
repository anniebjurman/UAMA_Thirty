package com.example.thirty

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.thirty.ui.theme.ThirtyTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private val diceButtonList = mutableListOf<ImageButton>()
    private lateinit var throwButton: Button
    private lateinit var throwCountView: TextView
    private lateinit var scoreList: Array<String>
    private lateinit var spinner: Spinner
    private var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)

//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

//        val rootLayout = root_layout.background as AnimationDrawable
//        rootLayout.setEnterFadeDuration(10)
//        rootLayout.setExitFadeDuration(5000)
//        rootLayout.start()

        diceButtonList.add(findViewById(R.id.dice_button_1))
        diceButtonList.add(findViewById(R.id.dice_button_2))
        diceButtonList.add(findViewById(R.id.dice_button_3))
        diceButtonList.add(findViewById(R.id.dice_button_4))
        diceButtonList.add(findViewById(R.id.dice_button_5))
        diceButtonList.add(findViewById(R.id.dice_button_6))
        throwButton = findViewById(R.id.throw_button)
        throwCountView = findViewById(R.id.throw_count)

        for (d in diceButtonList.indices) {
            diceButtonList[d].setOnClickListener { view: View ->
                game.toggleLockedDice(d)
                updateOneDiceImage(d)
            }
        }

        throwButton.setOnClickListener { view: View ->
            game.throwDice()
            updateDiceImages(game.getDiceList())
            updateNumThrowText()
        }

        //Spinner
        scoreList = resources.getStringArray(R.array.score_list)
        spinner = findViewById(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, scoreList)
            spinner.adapter = adapter
        }

        game.throwDice()
        updateDiceImages(game.getDiceList())
        updateNumThrowText()
    }

    private fun updateDiceImages(diceList: List<Dice>) {
        for (d in diceButtonList.indices) {
            diceButtonList[d].setImageResource(getImgPath(diceList[d], diceList[d].value))
        }
    }

    private fun updateNumThrowText() {
        val text = "Throws left: " + (3 - game.getNumThrows()).toString()
        throwCountView.text = text
    }

    private fun updateOneDiceImage(diceIndex: Int) {
        val diceList = game.getDiceList()
        diceButtonList[diceIndex].setImageResource(getImgPath(diceList[diceIndex], diceList[diceIndex].value))
    }

    private fun getImgPath(dice: Dice, num: Int): Int {
        if (dice.locked) {
            return when (num) {
                1 -> (R.drawable.orange1)
                2 -> (R.drawable.orange2)
                3 -> (R.drawable.orange3)
                4 -> (R.drawable.orange4)
                5 -> (R.drawable.orange5)
                6 -> (R.drawable.orange6)
                else -> {
                    throw error("error")
                }
            }
        } else {
            return when (num) {
                1 -> (R.drawable.blue1)
                2 -> (R.drawable.blue2)
                3 -> (R.drawable.blue3)
                4 -> (R.drawable.blue4)
                5 -> (R.drawable.blue5)
                6 -> (R.drawable.blue6)
                else -> {
                    throw error("error")
                }
            }
        }
    }
}