package com.example.calistung.ui.bebas_gambar

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.calistung.R
import com.example.calistung.databinding.ActivityBebasGambarBinding
import com.example.calistung.databinding.ActivityLearnBinding
import com.example.calistung.model.Learn
import com.example.calistung.ui.belajar.belajar_draw.LearnActivity
import com.example.calistung.ui.belajar.belajar_draw.LearnViewModel
import com.example.calistung.utils.loadImage
import kotlinx.coroutines.runBlocking

class bebasGambarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBebasGambarBinding
    private val model: BebasGambarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBebasGambarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        model.apply {
                binding.apply {
                    // takes input as Int
                    drawView.setStrokeWidth(30F)
                    btnClear.setOnClickListener {
                        drawView.clearCanvas()
                    }
                    btnCheck.setOnClickListener {
                        binding.cvCorrectness.backgroundTintList = model.lightBlue(resources)
                        runBlocking {
                            model.uploadImage(drawView.getBitmap(),resources=resources,context=this@bebasGambarActivity)
                            model.setIsStartedTrue()
                        }

                    }
                }

            correctnessText.observe(this@bebasGambarActivity) {
                binding.tvCorrectness.text = model.correctnessText.value.toString()
            }
            isStarted.observe(this@bebasGambarActivity) { isStarted ->
                if (isStarted) {
                    model.labelColor.observe(this@bebasGambarActivity) {
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