package com.example.tuktukgame

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {

    //Declare variables
    private lateinit var rootLayout :LinearLayout
    private lateinit var startBtn : Button
    private lateinit var mGameView: GameView
    private lateinit var score:TextView
    private lateinit var highestScore: TextView
    private var highScore = 0
    private lateinit var imageView3: ImageView
    private lateinit var imageView4: ImageView

    //Menu page
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // imported views
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highestScore = findViewById(R.id.Highestscore)
        mGameView = GameView(this, this)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)

        //Start button
        startBtn.setOnClickListener {
            mGameView.setBackgroundResource(R.drawable.road1)
            mGameView.resetGame()
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            highestScore.visibility = View.GONE
            imageView3.visibility = View.GONE
            imageView4.visibility = View.GONE
        }
    }

    //High score
    @SuppressLint("SetTextI18n")
    override fun closeGame(mScore: Int) {
        score.text = "Score : $mScore"
        if (mScore > highScore) {
            highScore = mScore // Update high score if necessary
            highestScore.text = "High Score : $highScore" // Update high score TextView
            }
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        highestScore.visibility = View.VISIBLE
    }


}