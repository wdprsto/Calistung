package com.example.calistung.ui.latihan.latihan_draw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.calistung.databinding.ActivityTrainBinding

import com.example.calistung.model.Train
import com.example.calistung.model.TrainQuestion
import java.util.*

class TrainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainBinding
    private val model: TrainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val item = intent.getParcelableExtra<TrainQuestion>(ITEM_SELECTED)
        model.apply {
            setTrainQuestion(item!!)
            setTts(this@TrainActivity)
            correctnessText.observe(this@TrainActivity) {
                binding.tvCorrectness.text = it
            }
            trainSelected.observe(this@TrainActivity) { mTrain ->
                binding.apply {
                    tvQuestion.text = mTrain.question
                    btnSpeak.setOnClickListener {
                        model.tts.observe(this@TrainActivity) {
                            it.speak(mTrain.question, TextToSpeech.QUEUE_FLUSH, null)
                        }
                    }
                    btnClear.setOnClickListener {
                        drawView.clearCanvas()
                    }
                    btnBack.setOnClickListener {
//                drawView.getBitmap()
//                        setNumber(model.number.value!!.minus(1))
                        prev()
                    }
                    btnNext.setOnClickListener {
//                drawView.getBitmap()
                        next()
                    }
                }
            }
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    companion object {
        const val ITEM_SELECTED = "item_selected"
    }
}