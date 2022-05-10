package com.example.calistung

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.calistung.databinding.ActivityLearnBinding
import com.example.calistung.model.Learn
import java.util.*


class LearnActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnBinding
    private lateinit var tts:TextToSpeech
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
            btnUndo.setOnClickListener {
                drawView.undo()
            }
            btnRedo.setOnClickListener {
                drawView.redo()
            }
            btnCheck.setOnClickListener {
                drawView.getBitmap()
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