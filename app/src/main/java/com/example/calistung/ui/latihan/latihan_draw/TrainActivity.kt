package com.example.calistung.ui.latihan.latihan_draw

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.divyanshu.draw.widget.MyPath
import com.divyanshu.draw.widget.PaintOptions
import com.example.calistung.R
import com.example.calistung.databinding.ActivityTrainBinding
import com.example.calistung.model.TrainQuestion
import com.example.calistung.ui.score.ScoreActivity
import java.util.*

class TrainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainBinding
    private val model: TrainViewModel by viewModels()
    private lateinit var point : String

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
                    drawView.setStrokeWidth(120F)

                    tvQuestion.text = mTrain.question
//                    model.setBitmapSelected( drawView.getBitmap())
                    btnSpeak.setOnClickListener {
                        model.tts.observe(this@TrainActivity) {
                            it.speak(mTrain.question, TextToSpeech.QUEUE_FLUSH, null)
                        }
                    }
                    btnClear.setOnClickListener {
                        drawView.clearCanvas()
                    }

                    btnCheck.setOnClickListener {
                        model.updateAnswer(drawView.getBitmap())
                    }



                    btnNext.setOnClickListener {
//                drawView.getBitmap()

                        drawView.clearCanvas()
                        Handler(Looper.getMainLooper()).postDelayed({

                            next()

                        }, 200)




                    }



//                        model.setBitmapSelected(drawView.getBitmap())











                }
            }



            model.points.observe(this@TrainActivity){
                scores(it)
            }
            model.finish.observe(this@TrainActivity){
                finish(it)
            }

            model.next.observe(this@TrainActivity){
                clear(it)
                if(it){
                    binding.cvCorrect.backgroundTintList =model.lightGreen(resources)
                }else{
                    binding.cvCorrect.backgroundTintList =model.ultraLightPink(resources)
                }
            }
            model.correctness.observe(this@TrainActivity){
                binding.tvCorrect.text = it
            }




        }
        /*model.clear.observe(this){
            showToast(it)

        }*/



    }


    private fun scores (score : String){
        point  = score
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
        }else{
            binding.apply {
                btnNext.isEnabled = false
                btnNext.isClickable = false
                btnNext.background.alpha = 64
                btnNext.setTextColor(Color.parseColor("#D3D3D3"))
            }
        }
    }
    private fun finish(finish : Boolean){
        if(finish) {
            binding.apply {
                btnNext.setText(getString(R.string.selesai))
                binding.btnNext.setBackgroundColor(Color.parseColor("#FFFFB2A6"))
                btnNext.setOnClickListener {
                    AlertDialog.Builder(it.context)
                        .setTitle("Akhiri")
                        .setMessage("Apakah anda ingin mengakhiri latihan?")
                        .setPositiveButton("Ya") { _, i ->
                            val intent = Intent(this@TrainActivity, ScoreActivity::class.java)
                            intent.putExtra(ScoreActivity.SCORE, point)
                            startActivity(intent)
                            finish()
                        }
                        .setNegativeButton("No") { _, i ->
                        }.show()

                }
            }
        }else{
            binding.btnNext.setText(getString(R.string.lanjut))
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