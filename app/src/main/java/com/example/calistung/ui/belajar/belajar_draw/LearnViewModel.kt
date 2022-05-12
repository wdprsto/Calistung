package com.example.calistung.ui.belajar.belajar_draw

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calistung.R
import com.example.calistung.model.Learn
import com.example.calistung.utils.getTextFromPhoto
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.*

class LearnViewModel : ViewModel() {

    private val _learn = MutableLiveData<Learn>()
    val learn
        get() = _learn

    private val _correctness = MutableLiveData<Boolean>()
    val correctness
        get() = _correctness

    private val _isStarted = MutableLiveData<Boolean>()
    val isStarted
        get() = _isStarted

    private val _correctnessText = MutableLiveData<String>()
    val correctnessText
        get() = _correctnessText

    private val _tts = MutableLiveData<TextToSpeech>()
    val tts
        get() = _tts

    init {
        _correctness.value = false
        _isStarted.value = false
    }

    /*fun setCorrectness(bitmap: Bitmap) {
        var temp=getTextFromPhoto(bitmap)==_learn.value?.answer
        _correctness.value = temp
        if (temp) {
            _correctnessText.value = "BENAR"
        } else {
            _correctnessText.value = "SALAH"
        }
    }*/
    fun setCorrectness(bitmap: Bitmap) {
//        getTextFromPhoto(bitmap)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap, 0)
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                // ...
                Log.e("TEMPIK", "HASIL : " + visionText.text)
                val temp = visionText.text == _learn.value?.answer
                _correctness.value = temp
                if (temp) {
                    _correctnessText.value = "BENAR"
                } else {
                    _correctnessText.value = "SALAH"
                }
//            tempText=visionText.text
//                Log.e("TEMPIK", "HASIL : " + visionText.text)
//            Log.e("TEMPIK","HASIL temp : "+tempText)
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                Log.e("TEMPIK", "EXEPTION : $e")
            }
    }
    fun setIsStartedTrue() {
        _isStarted.value = true
    }

    fun setLearn(learn: Learn) {
        _learn.value = learn
    }

    fun setTts(applicationContext: Context) {
        _tts.value = TextToSpeech(
            applicationContext
        ) { status ->
            if (status != TextToSpeech.ERROR) {
                _tts.value?.language = Locale.forLanguageTag("ID")
            }
        }
    }

    fun lightGreen(resources: Resources): ColorStateList =
        ColorStateList.valueOf(resources.getColor(R.color.light_green))

    fun ultraLightPink(resources: Resources): ColorStateList =
        ColorStateList.valueOf(resources.getColor(R.color.ultra_light_pink))

}