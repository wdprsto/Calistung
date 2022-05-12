package com.example.calistung.ui.latihan.latihan_draw

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calistung.model.Train
import com.example.calistung.model.TrainQuestion
import java.util.*

class TrainViewModel : ViewModel() {
    private val _trainQuestion = MutableLiveData<TrainQuestion>()
    val trainQuestion
        get() = _trainQuestion
    private val _trainSelected = MutableLiveData<Train>()
    val trainSelected
        get() = _trainSelected

//    private val _correctness = MutableLiveData<Boolean>()
//    val correctness
//        get() = _correctness
//
//    private val _isStarted = MutableLiveData<Boolean>()
//    val isStarted
//        get() = _isStarted

    private val _correctnessText = MutableLiveData<String>()
    val correctnessText
        get() = _correctnessText

    private val _tts = MutableLiveData<TextToSpeech>()
    val tts
        get() = _tts

    private val _number = MutableLiveData<Int>()
    val number
        get() = _number

    private val _score = MutableLiveData<Int>()
    val score
        get() = _score

    var map= mutableMapOf<Int,Train>()
    init {
        _number.value=12345
    }

    fun setTrainQuestion(trainQuestion: TrainQuestion) {
        _trainQuestion.value = trainQuestion
        val tempMap= mutableMapOf<Int,Train>()
        for(i in 1..trainQuestion.trains!!.size){
            tempMap[i] = trainQuestion.trains[i-1]
        }
        map=tempMap
        if(_number.value==12345) {
            setTrainSelected(trainQuestion.trains!![0])
            setNumber(1)
        }
    }

    fun setTrainSelected(trainSelected: Train) {
        _trainSelected.value = trainSelected
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

    fun setNumber(number: Int) {
        _number.value = number
        _correctnessText.value="SOAL $number DARI 10 SOAL"
    }
    fun next(){
        if(_number.value!!<map.size){
            setNumber(_number.value!!.plus(1))
            setTrainSelected(map[_number.value]!!)
        }
    }
    fun prev(){
        if(_number.value!!>1){
            setNumber(_number.value!!.minus(1))
            setTrainSelected(map[_number.value]!!)
        }
    }
    fun setScore(score: Int) {
        _score.value = score
    }

//    fun lightGreen(resources: Resources): ColorStateList =
//        ColorStateList.valueOf(resources.getColor(R.color.light_green))
//
//    fun ultraLightPink(resources: Resources): ColorStateList =
//        ColorStateList.valueOf(resources.getColor(R.color.ultra_light_pink))

}