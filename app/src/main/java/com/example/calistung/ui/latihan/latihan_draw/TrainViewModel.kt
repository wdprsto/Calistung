package com.example.calistung.ui.latihan.latihan_draw

import android.content.Context
import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calistung.model.Train
import com.example.calistung.model.TrainQuestion
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.*

class TrainViewModel : ViewModel() {
    private val _trainQuestion = MutableLiveData<TrainQuestion>()
    val trainQuestion
        get() = _trainQuestion
    private val _trainSelected = MutableLiveData<Train>()
    val trainSelected
        get() = _trainSelected
    private val _bitmapSelected = MutableLiveData<Bitmap>()
    val bitmapSelected
        get() = _bitmapSelected
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

    var map = mutableMapOf<Int, Train>()
    var numberAndScore = mutableMapOf<Int, Int>()
    var a = mutableMapOf<Int, String>()
    var myA = mutableMapOf<Int, String>()
    var bitmaps = mutableMapOf<Int, Bitmap>()

    init {
        _number.value = 12345
        _score.value = 0
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
    fun setBitmapSelected(bitmap: Bitmap){
        _bitmapSelected.value=bitmap
    }
    fun next() {
        if (_number.value!! < map.size) {
            setNumber(_number.value!!.plus(1))
            setTrainSelected(map[_number.value]!!)
            if(bitmaps[_number.value!!]!=null)
                setBitmapSelected(bitmaps[_number.value!!]!!)
        }
    }

    fun prev() {
        if (_number.value!! > 1) {
            setNumber(_number.value!!.minus(1))
            setTrainSelected(map[_number.value]!!)
            if(bitmaps[_number.value!!]!=null)
                setBitmapSelected(bitmaps[_number.value!!]!!)
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
    fun updateScore() {
        var temp = 0
        for (i in 1..myA.size) {
            if (a[i] == myA[i]) {
                temp++
            }
        }
        /*for(i in 1..numberAndScore.size){
                temp+=numberAndScore[i]!!
            }*/
        Log.e("TEMPIK", "TEMP : " + temp)
        Log.e("TEMPIK", "number and score : " + numberAndScore)
        _score.value = temp
//        _correctnessText.value = "SOAL $number DARI ${map.size} SOAL || nilai anda ${_score.value}"
    }

    fun updateAnswer(bitmap: Bitmap) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap, 0)
        bitmaps[_number.value!!] = bitmap
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                // ...
                Log.e("TEMPIK", "HASIL : " + visionText.text)
                Log.e("TEMPIK", "SCORE : " + _score.value)
                val temp = visionText.text == _trainSelected.value?.answer
                if (temp) {
                    myA[_number.value!!] = visionText.text
//                    numberAndScore[_number.value!!]=1
//                    plusScore()
//                    _correctnessText.value = "BENAR"
                } else {
                    myA[_number.value!!] = visionText.text
//                    numberAndScore[_number.value!!]=0
//                    minusScore()
//                    _correctnessText.value = "SALAH"
                }
                updateScore()
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
//    fun lightGreen(resources: Resources): ColorStateList =
//        ColorStateList.valueOf(resources.getColor(R.color.light_green))
//
//    fun ultraLightPink(resources: Resources): ColorStateList =
//        ColorStateList.valueOf(resources.getColor(R.color.ultra_light_pink))

}