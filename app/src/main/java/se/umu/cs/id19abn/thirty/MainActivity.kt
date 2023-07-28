package se.umu.cs.id19abn.thirty

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity

// Main Activity, controls the game part of the app
class MainActivity : ComponentActivity() {

    // Declare variables
    private val diceButtonList = mutableListOf<ImageButton>()
    private lateinit var throwButton: Button
    private lateinit var chooseButton: Button
    private lateinit var addPointsButton: Button
    private lateinit var nextRoundButton: Button
    private lateinit var viewResultsButton: Button
    private lateinit var throwCountView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var statusAddTextView: TextView
    private lateinit var totalPointsTextView: TextView
    private lateinit var scoreList: Array<String>
    private lateinit var spinner: Spinner
    private var game = Game()

    // Save data if activity is destroyed
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("gameObject", game)
        outState.putString("stateMessage", statusAddTextView.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)

        // Initialize variables
        statusAddTextView = findViewById(R.id.status_add)
        totalPointsTextView = findViewById(R.id.total_points)
        descriptionTextView = findViewById(R.id.description)

        // Restore saved data if activity is destroyed and immediately recreated
        if (savedInstanceState != null) {
            game = savedInstanceState.getParcelable("gameObject")!!

            val totalPointsString = "Points: " + game.getTotalPoints().toString()
            totalPointsTextView.text = totalPointsString

            statusAddTextView.text = savedInstanceState.getString("stateMessage")
            updateDescriptionText()
        }

        // Initialize variables
        diceButtonList.add(findViewById(R.id.dice_button_1))
        diceButtonList.add(findViewById(R.id.dice_button_2))
        diceButtonList.add(findViewById(R.id.dice_button_3))
        diceButtonList.add(findViewById(R.id.dice_button_4))
        diceButtonList.add(findViewById(R.id.dice_button_5))
        diceButtonList.add(findViewById(R.id.dice_button_6))
        throwButton = findViewById(R.id.throw_button)
        chooseButton = findViewById(R.id.choose_button)
        addPointsButton = findViewById(R.id.add_points_button)
        nextRoundButton = findViewById(R.id.next_round_button)
        viewResultsButton = findViewById(R.id.view_results_button)
        throwCountView = findViewById(R.id.throw_count)

        // Set up spinner for choosing level
        scoreList = resources.getStringArray(R.array.score_list)
        spinner = findViewById(R.id.spinner)

        // Create adapter with the different levels to choose
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, scoreList)
        spinner.adapter = adapter

        // Setup clickListeners
        for (d in diceButtonList.indices) {
            // Click -> Mark that dice
            diceButtonList[d].setOnClickListener { view: View ->
                if (game.getCurrentStep() != 3) {
                    game.toggleLockedDice(d)
                    updateOneDiceImage(d)
                }
            }
        }

        // Click -> Throw dice
        throwButton.setOnClickListener { view: View ->
            game.throwDice()
            updateDiceImages(game.getDiceList())
            updateRoundThrowText()
            updateDescriptionText()
            updateElementVisibility()
        }

        // Click -> Set chosen level
        chooseButton.setOnClickListener { view: View ->
            // Check is level is already used
            if (game.checkUsedLevel(spinner.selectedItem.toString())) {
                // If chosen level is ok -> update game
                statusAddTextView.text = ""
                game.setChosenLevel(spinner.selectedItem.toString())
                game.setCurrentStep(4)
                updateDescriptionText()
                updateElementVisibility()
                updateDiceImages(game.getDiceList())
            } else {
                // If chosen level is already used -> notify player
                val errorString = "Already used level ${spinner.selectedItem}, choose another level!"
                statusAddTextView.text = errorString
            }
        }

        // Click -> Add points for the chosen dice
        addPointsButton.setOnClickListener { view: View ->
            updateDescriptionText()
            updateElementVisibility()

            // Count points
            val status = game.countPoints()
            statusAddTextView.text = status
            updateDiceImages(game.getDiceList())

            // Update total points text
            val totalPointsString = "Points: " + game.getTotalPoints().toString()
            totalPointsTextView.text = totalPointsString
        }

        // Click -> Go to next round
        nextRoundButton.setOnClickListener { view: View ->
            // Go back to first step of the game
            game.setCurrentStep(1)
            // Update texts and element visibility for that step
            updateDescriptionText()
            updateElementVisibility()
            updateDiceImages(game.getDiceList())
            updateRoundThrowText()
            statusAddTextView.text = ""
        }

        // Click -> Start result activity
        viewResultsButton.setOnClickListener {view: View ->
            val intent = Intent(this, RestultsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            // Save data to view in the results view
            intent.putExtra("historyScores", game.getHistoryScores())
            intent.putExtra("totalScore", game.getTotalPoints())
            startActivity(intent)
        }

        // Init game
        updateDiceImages(game.getDiceList())
        updateRoundThrowText()
        updateElementVisibility()
    }

    // Method for updating dice images
    private fun updateDiceImages(diceList: List<Dice>) {
        for (d in diceButtonList.indices) {
            diceButtonList[d].setImageResource(getImgPath(diceList[d], diceList[d].value))
        }
    }

    // Method for updating text about the current round and throw
    private fun updateRoundThrowText() {
        val text = "Round " + game.getCurrentRound().toString() + " / Throw " + game.getCurrentThrow().toString()
        throwCountView.text = text
    }

    // Method for updating the description text depending on the current step
    private fun updateDescriptionText() {
        when (game.getCurrentStep()) {
            1 -> descriptionTextView.text = getText(R.string.step1)
            2 -> descriptionTextView.text = getText(R.string.step2)
            3 -> descriptionTextView.text = getText(R.string.step3)
            4 -> descriptionTextView.text = getText(R.string.step4)
            5 -> descriptionTextView.text = getText(R.string.step5)
            else -> {
                throw error("error")
            }
        }
    }

    // Method for updating which elements and buttons that are going to be visible, depending on the current step
    private fun updateElementVisibility() {
        when (game.getCurrentStep()) {
            1, 2 -> {
                throwButton.visibility = View.VISIBLE
                chooseButton.visibility = View.GONE
                spinner.visibility = View.GONE
                addPointsButton.visibility = View.GONE
                nextRoundButton.visibility = View.GONE
                viewResultsButton.visibility = View.GONE
            }
            3 -> {
                throwButton.visibility = View.GONE
                chooseButton.visibility = View.VISIBLE
                spinner.visibility = View.VISIBLE
                addPointsButton.visibility = View.GONE
                nextRoundButton.visibility = View.GONE
                viewResultsButton.visibility = View.GONE
            }
            4 -> {
                throwButton.visibility = View.GONE
                chooseButton.visibility = View.GONE
                spinner.visibility = View.GONE
                addPointsButton.visibility = View.VISIBLE
                if (game.getCurrentRound() == game.getMaxNumRounds()) {
                    nextRoundButton.visibility = View.GONE
                    viewResultsButton.visibility = View.VISIBLE
                } else {
                    nextRoundButton.visibility = View.VISIBLE
                    viewResultsButton.visibility = View.GONE
                }
            }
        }
    }

    // Method for updating one specific dice image
    private fun updateOneDiceImage(diceIndex: Int) {
        val diceList = game.getDiceList()
        diceButtonList[diceIndex].setImageResource(getImgPath(diceList[diceIndex], diceList[diceIndex].value))
    }

    // Method to get what color the dice should have
    private fun getImgPath(dice: Dice, num: Int): Int {
        if (game.getCurrentThrow() == 0 || dice.counted) {
            // Beige placeholder if no dice have been thrown or dice have already been counted
            return R.drawable.placeholder
        } else if (dice.locked) {
            // Orange dice if player have locked that dice
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
        } else if (game.getCurrentStep() == 3) {
            // Beige dice if level is about to be chosen
            return when (num) {
                1 -> (R.drawable.beige1)
                2 -> (R.drawable.beige2)
                3 -> (R.drawable.beige3)
                4 -> (R.drawable.beige4)
                5 -> (R.drawable.beige5)
                6 -> (R.drawable.beige6)
                else -> {
                    throw error("error")
                }
            }
        }
        else {
            // Otherwise green dice
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
        }
    }
}