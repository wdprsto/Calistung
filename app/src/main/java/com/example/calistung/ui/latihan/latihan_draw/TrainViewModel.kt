package com.example.calistung.ui.latihan.latihan_draw

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyanshu.draw.widget.MyPath
import com.divyanshu.draw.widget.PaintOptions
import com.example.calistung.R
import com.example.calistung.model.Train
import com.example.calistung.model.TrainQuestion
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.LinkedHashMap

class TrainViewModel : ViewModel() {
    private val _trainQuestion = MutableLiveData<TrainQuestion>()
    val trainQuestion
        get() = _trainQuestion
    private val _trainSelected = MutableLiveData<Train>()
    val trainSelected
        get() = _trainSelected
    private val _bitmapSelected = MutableLiveData<Path>()
    val bitmapSelected
        get() = _bitmapSelected
    private val _correctness = MutableLiveData<String>()
    val correctness
        get() = _correctness
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

    private val _keepBitmaps = MutableLiveData<Bitmap>()
    val keepBitmaps
        get() = _keepBitmaps

    private val _points = MutableLiveData<String>()
    val points: LiveData<String> = _points
    private val _finish = MutableLiveData<Boolean>()
    val finish: LiveData<Boolean> = _finish

    private val _next = MutableLiveData<Boolean>()
    val next: LiveData<Boolean> = _next



    var map = mutableMapOf<Int, Train>()
    var numberAndScore = mutableMapOf<Int, Int>()
    var a = mutableMapOf<Int, String>()
    var myA = mutableMapOf<Int, String>()
    var temp = 0
    val point = mutableMapOf<Int, Int>()


    init {
        _number.value = 12345
        _score.value = 0
        _next.value = false

    }


    fun setTrainQuestion(trainQuestion: TrainQuestion) {
        _trainQuestion.value = trainQuestion
        val tempMap = mutableMapOf<Int, Train>()
        for (i in 1..trainQuestion.trains!!.size) {
            tempMap[i] = trainQuestion.trains[i - 1]
            a[i] = trainQuestion.trains[i - 1].answer!!
            myA[i] = ""
        }
        map = tempMap
        if (_number.value == 12345) {
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
//        _correctnessText.value = "SOAL $number DARI ${map.size} SOAL || nilai anda ${_score.value}"
        _correctnessText.value = "SOAL $number DARI ${map.size} SOAL"
    }



    fun next() {


        if (_number.value!! < map.size) {
            setNumber(_number.value!!.plus(1))
            setTrainSelected(map[_number.value]!!)




        }
        if (_number.value!! >= map.size) {
            _finish.value = true
            point.values.sum()
            _points.value = point.values.sum().toString()
            Log.e("TEMPIK", "POINT : ${point.values.sum()}")
        }
    }



    //    fun plusScore() {
//        setScore(_score.value?.plus(1)!!)
//    }
//    fun minusScore() {
//        setScore(_score.value?.minus(1)!!)
//    }
//    fun setScore(score:Int){
//        _score.value=score
//    }


    fun updateScore(isTrue: Boolean, position: Int) {

        if (isTrue) {
            point[position] = 1
        } else {
            point[position] = 0
        }


        /*for(i in 1..numberAndScore.size){
            temp+=numberAndScore[i]!!
        }*/
        _score.value = temp
        Log.e("TEMPIK", "TEMP : ${_score.value}")
        Log.e("TEMPIK", "number and score : " + numberAndScore)

//        _correctnessText.value = "SOAL $number DARI ${map.size} SOAL || nilai anda ${_score.value}"
    }

    fun updateAnswer(bitmap: Bitmap) {
        _next.value = false

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap, 0)
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                // ..
                val temp = visionText.text == _trainSelected.value?.answer
                if (temp) {

                    updateScore(temp, _number.value!!)
                    Log.e("TEMPIK", "HASIL : Benar ")
                    Log.e("TEMPIK", "HASIL : " + visionText.text)
                    Log.e("TEMPIK", "SCORE : ${_score.value}")
                    _next.value = true
                    _correctness.value = "BENAR"
                    myA[_number.value!!] = visionText.text


                } else {
                    updateScore(temp, _number.value!!)
                    Log.e("TEMPIK", "HASIL : Salah ")
                    Log.e("TEMPIK", "HASIL : " + visionText.text)
                    Log.e("TEMPIK", "SCORE : ${_score.value}")
                    _next.value = false
                    _correctness.value = "SALAH"
                    myA[_number.value!!] = visionText.text


//                    numberAndScore[_number.value!!]=0
//                    minusScore()
//                    _correctnessText.value = "SALAH"
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
    fun lightGreen(resources: Resources): ColorStateList =
         ColorStateList.valueOf(resources.getColor(R.color.light_green))

     fun ultraLightPink(resources: Resources): ColorStateList =
         ColorStateList.valueOf(resources.getColor(R.color.ultra_light_pink))


}