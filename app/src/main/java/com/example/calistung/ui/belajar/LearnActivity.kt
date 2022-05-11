package com.example.calistung.ui.belajar

import android.content.res.ColorStateList
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.calistung.R
import com.example.calistung.databinding.ActivityLearnBinding
import com.example.calistung.model.Learn
import java.util.*


class LearnActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnBinding
    private lateinit var tts:TextToSpeech
    private var correctness=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item=intent.getParcelableExtra<Learn>(ITEM_SELECTED)
        tts = TextToSpeech(
            applicationContext
        ) { status ->
            if (status != TextToSpeech.ERROR) {
                tts.language = Locale.forLanguageTag("ID")
            }
        }
        binding.apply {
            imageView.loadImage(item?.gifLink)
            btnSpeak.setOnClickListener {
                tts.speak(item?.answer,TextToSpeech.QUEUE_FLUSH, null)
            }
            btnClear.setOnClickListener {
                drawView.clearCanvas()
            }
            btnCheck.setOnClickListener {
//                drawView.getBitmap()
                correctness=!correctness
                if(correctness){
                    cvCorrectness.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.light_green))
                    tvCorrectness.text="BENAR"
                }else{
                    cvCorrectness.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.ultra_light_pink))
                    tvCorrectness.text="SALAH"
                }

            }
        }
    }
    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
    companion object{
        const val ITEM_SELECTED="item_selected"
    }
}