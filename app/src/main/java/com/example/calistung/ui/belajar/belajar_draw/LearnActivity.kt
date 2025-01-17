package com.example.calistung.ui.belajar.belajar_draw

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.calistung.databinding.ActivityLearnBinding
import com.example.calistung.model.Learn
import com.example.calistung.utils.loadImage
import kotlinx.coroutines.runBlocking


class LearnActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearnBinding
    private val model: LearnViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val item = intent.getParcelableExtra<Learn>(ITEM_SELECTED)
        supportActionBar?.title=item?.name
        model.apply {
            setLearn(item!!)
            setTts(this@LearnActivity)
            learn.observe(this@LearnActivity) { mLearn ->
                binding.apply {
                    // takes input as Int
                    drawView.setStrokeWidth(30F)
                    imageView.loadImage(mLearn.gifLink)
                    btnSpeak.setOnClickListener {
                        model.tts.observe(this@LearnActivity) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                it.speak(mLearn.vo, TextToSpeech.QUEUE_FLUSH, null,null)
                            } else {
                                it.speak(mLearn.vo, TextToSpeech.QUEUE_FLUSH, null,null)
                            }

                        }
                    }
                    btnClear.setOnClickListener {
                        drawView.clearCanvas()
                    }
                    btnCheck.setOnClickListener {
                        binding.cvCorrectness.backgroundTintList = model.lightBlue(resources)
                        runBlocking {
                            model.uploadImage(drawView.getBitmap(),resources=resources,context=this@LearnActivity)
                            model.setIsStartedTrue()
                        }

                    }
                }
            }
            correctnessText.observe(this@LearnActivity) {
                binding.tvCorrectness.text = model.correctnessText.value.toString()
            }
            isStarted.observe(this@LearnActivity) { isStarted ->
                if (isStarted) {
                    model.labelColor.observe(this@LearnActivity) {
                        binding.cvCorrectness.backgroundTintList = it
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