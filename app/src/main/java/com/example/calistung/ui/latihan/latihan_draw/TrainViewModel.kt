package com.example.calistung.ui.latihan.latihan_draw

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyanshu.draw.widget.DrawView
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

    private val _bitmaps = MutableLiveData<MutableMap<Int,Fragment>>()
    val bitmaps: LiveData<MutableMap<Int,Fragment>> = _bitmaps


    var map = mutableMapOf<Int, Train>()
    var numberAndScore = mutableMapOf<Int, Int>()
    var a = mutableMapOf<Int, String>()
    var myA = mutableMapOf<Int, String>()
//    var bitmaps = MutableLiveData<MutableMap<Int, Fragment>>()
    var currentFragment=MutableLiveData<Fragment>()
    var temp = 0
    val point = mutableMapOf<Int, Int>()


    init {
        _number.value = 12345
        _score.value = 0
        bitmaps.value?.set(1, DrawFragment())

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

    fun setBitmapSelected(bitmap: Bitmap) {
        _bitmapSelected.value = bitmap

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

    fun prev() {
        if (_number.value!! > 1) {
            setNumber(_number.value!!.minus(1))
            setTrainSelected(map[_number.value]!!)



        }
        if (_number.value!! <= map.size) {
            _finish.value = false
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

    fun saveDrawView(drawFragment: Fragment){
        bitmaps.value?.set(number.value!!, drawFragment)
//        Log.e("TEMPIK",number.toString()+"=="+bitmaps.value[number.value!!].toString())
        Log.e("TEMPIK all",bitmaps.toString())
//        Log.e("TEMPIK","nomorzz"+number.value.toString())
    }
    fun getCurrentF()= bitmaps.value?.get(number.value!!)

    fun updateAnswer(bitmap: Bitmap) {
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
                    myA[_number.value!!] = visionText.text


                } else {
                    updateScore(temp, _number.value!!)
                    Log.e("TEMPIK", "HASIL : Salah ")
                    Log.e("TEMPIK", "HASIL : " + visionText.text)
                    Log.e("TEMPIK", "SCORE : ${_score.value}")
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
    /* fun lightGreen(resources: Resources): ColorStateList =
         ColorStateList.valueOf(resources.getColor(R.color.light_green))

     fun ultraLightPink(resources: Resources): ColorStateList =
         ColorStateList.valueOf(resources.getColor(R.color.ultra_light_pink))*/


}