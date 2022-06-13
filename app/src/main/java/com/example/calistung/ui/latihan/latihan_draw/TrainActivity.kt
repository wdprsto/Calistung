package com.example.calistung.ui.latihan.latihan_draw

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.calistung.R
import com.example.calistung.databinding.ActivityTrainBinding
import com.example.calistung.model.TrainQuestion

class TrainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainBinding
    private val model: TrainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val item = intent.getParcelableExtra<TrainQuestion>(ITEM_SELECTED)
        supportActionBar?.title = item?.name
        model.apply {
            setTrainQuestion(item!!)
            setTts(this@TrainActivity)
            correctnessText.observe(this@TrainActivity) {
                binding.tvCorrectness.text = it
            }
            trainSelected.observe(this@TrainActivity) { mTrain ->
                binding.apply {
                    drawView.setStrokeWidth(30F)
                    btnNext.isEnabled = false
                    btnNext.isClickable = false
                    btnNext.background.alpha = 64
                    btnNext.setTextColor(Color.parseColor("#D3D3D3"))
                    tvQuestion.text = mTrain.question
                    btnSpeak.setOnClickListener {
                        model.tts.observe(this@TrainActivity) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                it.speak(mTrain.question, TextToSpeech.QUEUE_FLUSH, null, null)
                            } else {
                                it.speak(mTrain.question, TextToSpeech.QUEUE_FLUSH, null, null)
                            }
                        }
                    }
                    btnClear.setOnClickListener {
                        drawView.clearCanvas()
                    }

                    btnCheck.setOnClickListener {
                        model.uploadImage(
                            drawView.getBitmap(),
                            resources = resources,
                            context = this@TrainActivity
                        )
                    }
                    btnNext.setOnClickListener {
                        drawView.clearCanvas()
                        binding.tvCorrect.text = ""
                        setLightBlue(resources)
                        next()
                    }
                }
            }
            model.labelColor.observe(this@TrainActivity) {
                binding.cvCorrect.backgroundTintList = it
            }
            model.finish.observe(this@TrainActivity) {
                finish(it)
            }

            model.next.observe(this@TrainActivity) {
                clear(it)
                if (it) {
                    binding.cvCorrect.backgroundTintList = model.lightGreen(resources)
                } else {
                    binding.cvCorrect.backgroundTintList = model.ultraLightPink(resources)
                }
            }
            model.correctness.observe(this@TrainActivity) {
                binding.tvCorrect.text = it
            }

        }
    }

    private fun clear(clear: Boolean) {
        if (clear) {
            binding.apply {
                btnNext.isEnabled = true
                btnNext.isClickable = true
                btnNext.setBackgroundColor(Color.parseColor("#FF6EE1AB"))
                btnNext.setTextColor(Color.parseColor("#FF000000"))
                btnNext.background.alpha = 255

            }
        } else {
            binding.apply {
                btnNext.isEnabled = false
                btnNext.isClickable = false
                btnNext.background.alpha = 64
                btnNext.setTextColor(Color.parseColor("#D3D3D3"))
            }
        }
    }

    private fun finish(finish: Boolean) {
        if (finish) {
            binding.apply {
                btnNext.text = getString(R.string.selesai)
                binding.btnNext.setBackgroundColor(Color.parseColor("#FFFFB2A6"))
                btnNext.setOnClickListener {
                    AlertDialog.Builder(it.context)
                        .setTitle("Akhiri")
                        .setMessage("Apakah anda ingin mengakhiri latihan?")
                        .setPositiveButton("Ya") { _, i ->
                            onBackPressed()
                            finish()
                        }
                        .setNegativeButton("No") { _, i ->
                        }.show()
                }
            }
        } else {
            binding.btnNext.text = getString(R.string.lanjut)
            binding.btnNext.setBackgroundColor(Color.parseColor("#FF6EE1AB"))
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