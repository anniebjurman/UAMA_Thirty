package se.umu.cs.id19abn.thirty

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.util.ArrayList

// Results Activity, controls the last view that displays the results.
class RestultsActivity : ComponentActivity() {

    // Declare variables
    private lateinit var totalPointsTextView: TextView
    private val dataTextViewList: ArrayList<TextView> = arrayListOf()
    private val totalStringList: ArrayList<String> = arrayListOf("", "", "", "", "", "", "", "", "", "",)
    private var totalScore: Int = -1
    private var historyScores: ArrayList<Score>? = arrayListOf()
    private lateinit var restartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restults)

        // Find and setup view elements
        dataTextViewList.add(findViewById(R.id.round1_data))
        dataTextViewList.add(findViewById(R.id.round2_data))
        dataTextViewList.add(findViewById(R.id.round3_data))
        dataTextViewList.add(findViewById(R.id.round4_data))
        dataTextViewList.add(findViewById(R.id.round5_data))
        dataTextViewList.add(findViewById(R.id.round6_data))
        dataTextViewList.add(findViewById(R.id.round7_data))
        dataTextViewList.add(findViewById(R.id.round8_data))
        dataTextViewList.add(findViewById(R.id.round9_data))
        dataTextViewList.add(findViewById(R.id.round10_data))
        totalPointsTextView = findViewById(R.id.total_points)
        restartButton = findViewById(R.id.restart_button)

        // Listener for the restart button
        // When clicked the MainActivity is restarted
        restartButton.setOnClickListener { view: View ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        // Get result data from intent
        val intent = intent
        val extras: Bundle? = intent.extras
        if (extras != null) {
            historyScores = extras.getParcelableArrayList("historyScores")
            totalScore = extras.getInt("totalScore", -1)
        }

        // Run method to set result text to view elements
        setResultTexts()
    }

    // Method to set result text
    private fun setResultTexts() {
        totalPointsTextView.text = totalScore.toString()
        setDataTextViewList()

        for (d in dataTextViewList.indices) {
            dataTextViewList[d].text = totalStringList[d]
        }
    }

    // Method to set result text in different views
    private fun setDataTextViewList() {
        historyScores?.forEach {
            var string = ""
            it.dice.forEach {
                string += "$it, "
            }
            string += "--> ${it.sum} \n"

            when (it.round) {
                1 -> totalStringList[0] += string
                2 -> totalStringList[1] += string
                3 -> totalStringList[2] += string
                4 -> totalStringList[3] += string
                5 -> totalStringList[4] += string
                6 -> totalStringList[5] += string
                7 -> totalStringList[6] += string
                8 -> totalStringList[7] += string
                9 -> totalStringList[8] += string
                10 -> totalStringList[9] += string
                else -> {
                    print("Error!")
                }
            }
        }
    }
}