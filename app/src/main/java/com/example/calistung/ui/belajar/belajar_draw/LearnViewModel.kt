package com.example.calistung.ui.belajar.belajar_draw

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.speech.tts.TextToSpeech
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calistung.R
import com.example.calistung.model.Learn
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

    fun setCorrectness(correctness: Boolean) {
        _correctness.value = correctness
        if (correctness) {
            _correctnessText.value = "BENAR"
        } else {
            _correctnessText.value = "SALAH"
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