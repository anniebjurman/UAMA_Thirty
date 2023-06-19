package se.umu.cs.id19abn.thirty

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private val diceButtonList = mutableListOf<ImageButton>()
    private lateinit var throwButton: Button
    private lateinit var throwCountView: TextView
    private lateinit var descriptionTextView: TextView
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
        descriptionTextView = findViewById(R.id.description)

        for (d in diceButtonList.indices) {
            diceButtonList[d].setOnClickListener { view: View ->
                game.toggleLockedDice(d)
                updateOneDiceImage(d)
            }
        }

        throwButton.setOnClickListener { view: View ->
            game.throwDice()
            updateDiceImages(game.getDiceList())
            updateRoundThrowText()
            updateDescriptionText()
        }

        //Spinner
        scoreList = resources.getStringArray(R.array.score_list)
        spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, scoreList)
        spinner.adapter = adapter

        // Init game
        updateDiceImages(game.getDiceList())
        updateRoundThrowText()
    }

    private fun updateDiceImages(diceList: List<Dice>) {
        for (d in diceButtonList.indices) {
            diceButtonList[d].setImageResource(getImgPath(diceList[d], diceList[d].value))
        }
    }

    private fun updateRoundThrowText() {
        val text = "Round " + game.getCurrentRound().toString() + " / Throw " + game.getCurrentThrow().toString()
        throwCountView.text = text
    }

    private fun updateDescriptionText(): Int {
        Log.d("updateDescriptionText, step: ", game.getCurrentStep().toString())
        return when (game.getCurrentStep()) {
            1 -> (R.string.step1)
            2 -> (R.string.step2)
            3 -> (R.string.step3)
            4 -> (R.string.step4)
            5 -> (R.string.step5)
            else -> {
                throw error("error")
            }
        }
    }

    private fun updateOneDiceImage(diceIndex: Int) {
        val diceList = game.getDiceList()
        diceButtonList[diceIndex].setImageResource(getImgPath(diceList[diceIndex], diceList[diceIndex].value))
    }

    private fun getImgPath(dice: Dice, num: Int): Int {
        if (game.getCurrentThrow() == 0) {
            return R.drawable.placeholder
        } else if (game.getRoundIsOver()) {
            return when (num) {
                1 -> (R.drawable.green1)
                2 -> (R.drawable.green2)
                3 -> (R.drawable.green3)
                4 -> (R.drawable.green4)
                5 -> (R.drawable.green5)
                6 -> (R.drawable.green6)
                else -> {
                    throw error("error")
                }
            }
        } else if (dice.locked) {
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