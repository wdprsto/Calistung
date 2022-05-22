package com.example.calistung.ui.score

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calistung.databinding.ActivityScoreBinding
import com.example.calistung.model.TrainQuestion
import com.example.calistung.ui.latihan.latihan_draw.TrainActivity
import com.example.calistung.ui.menu.MenuPageActivity

class ScoreActivity : AppCompatActivity() {
    private lateinit var scoreActivity: ActivityScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scoreActivity = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(scoreActivity.root)

        val item = intent.getStringExtra(SCORE)

        scoreActivity.apply {
            score.text = item
            btnFinish.setOnClickListener {
                val intent = Intent(this@ScoreActivity, MenuPageActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


    }
    companion object{
        const val SCORE = "score"
    }
}