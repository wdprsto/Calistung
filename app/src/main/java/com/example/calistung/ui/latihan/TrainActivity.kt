package com.example.calistung.ui.latihan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.calistung.databinding.ActivityTrainBinding
import com.example.calistung.model.Train
import java.util.*

class TrainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainBinding
    private lateinit var tts: TextToSpeech
    private var count=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTrainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item=intent.getParcelableExtra<Train>(ITEM_SELECTED)
        tts = TextToSpeech(
            applicationContext
        ) { status ->
            if (status != TextToSpeech.ERROR) {
                tts.language = Locale.forLanguageTag("ID")
            }
        }
        binding.apply {
            tvCorrectness.text="SOAL $count DARI 10 SOAL"
            tvQuestion.text=item?.question
            btnSpeak.setOnClickListener {
                tts.speak(item?.answer, TextToSpeech.QUEUE_FLUSH, null)
            }
            btnClear.setOnClickListener {
                drawView.clearCanvas()
            }
            btnBack.setOnClickListener {
//                drawView.getBitmap()
                count--
                tvCorrectness.text="SOAL $count DARI 10 SOAL"
            }
            btnNext.setOnClickListener {
//                drawView.getBitmap()
                count++
                tvCorrectness.text="SOAL $count DARI 10 SOAL"
            }
        }
    }
    companion object{
        const val ITEM_SELECTED="item_selected"
    }
}