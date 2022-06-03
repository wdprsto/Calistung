package com.example.calistung.ui.belajar.belajar_draw

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.calistung.databinding.ActivityLearnBinding
import com.example.calistung.model.Learn
import com.example.calistung.utils.loadImage


class LearnActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnBinding
    private val model:LearnViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val item = intent.getParcelableExtra<Learn>(ITEM_SELECTED)
        model.apply {
            setLearn(item!!)
            setTts(this@LearnActivity)
            learn.observe(this@LearnActivity) {mLearn->
                binding.apply {
                    // takes input as Int
                    drawView.setStrokeWidth(120F)
                    imageView.loadImage(mLearn.gifLink)
                    btnSpeak.setOnClickListener {
                        model.tts.observe(this@LearnActivity){
                            it.speak(mLearn.answer, TextToSpeech.QUEUE_FLUSH, null)
                        }
                    }
                    btnClear.setOnClickListener {
                        drawView.clearCanvas()
                    }
                    btnCheck.setOnClickListener {
//                drawView.getBitmap()

                        model.bitmapToFile(drawView.getBitmap())

                        model.setIsStartedTrue()
                    }
                }
            }
            correctnessText.observe(this@LearnActivity){
                binding.tvCorrectness.text = model.correctnessText.value.toString()
            }
            isStarted.observe(this@LearnActivity){ isStarted ->
                if(isStarted){
                    model.correctness.observe(this@LearnActivity){
                        if (it) {
                            binding.cvCorrectness.backgroundTintList =model.lightGreen(resources)
                        } else {
                            binding.cvCorrectness.backgroundTintList =model.ultraLightPink(resources)
                        }
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