package se.umu.cs.id19abn.thirty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.util.ArrayList


class RestultsActivity : ComponentActivity() {

    private lateinit var resultsTextView: TextView
    private lateinit var totalPointsTextView: TextView

//    private lateinit var intent: Intent
    private lateinit var resultsList: ArrayList<String>
    private var totalScore: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restults)

        resultsTextView = findViewById(R.id.results_text_view)
        totalPointsTextView = findViewById(R.id.total_points)

        val intent = intent
        resultsList = intent.getStringArrayListExtra("scoreList") as ArrayList<String>
        totalScore = intent.getIntExtra("totalScore", -1)

        Log.d("RESULTS", totalScore.toString())

        var finalResultString = ""
        resultsList.forEach {
            finalResultString += "$it \n"
        }

//        val tmp = "hello"
        resultsTextView.text = finalResultString
        totalPointsTextView.text = totalScore.toString()
    }
}