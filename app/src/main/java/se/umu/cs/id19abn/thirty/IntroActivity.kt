package se.umu.cs.id19abn.thirty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity

// Intro Activity, controls the first view the player see
class IntroActivity : ComponentActivity() {

    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        startButton = findViewById(R.id.start_button)

        // Go to main activity when user clicks "start game"
        startButton.setOnClickListener { view: View ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}