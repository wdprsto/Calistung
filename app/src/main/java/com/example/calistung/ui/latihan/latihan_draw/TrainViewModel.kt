package com.example.calistung.ui.latihan.latihan_draw

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyanshu.draw.widget.MyPath
import com.divyanshu.draw.widget.PaintOptions
import com.example.calistung.R
import com.example.calistung.model.Predict
import com.example.calistung.model.Train
import com.example.calistung.model.TrainQuestion
import com.example.calistung.service.ApiConfig
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.LinkedHashMap

class TrainViewModel : ViewModel() {
    private val _trainQuestion = MutableLiveData<TrainQuestion>()
    val trainQuestion
        get() = _trainQuestion
    private val _trainSelected = MutableLiveData<Train>()
    val trainSelected
        get() = _trainSelected
//    private val _bitmapSelected = MutableLiveData<Path>()
//    val bitmapSelected
//        get() = _bitmapSelected
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
//
//    private val _points = MutableLiveData<String>()
//    val points: LiveData<String> = _points
    private val _finish = MutableLiveData<Boolean>()
    val finish: LiveData<Boolean> = _finish

    private val _next = MutableLiveData<Boolean>()
    val next: LiveData<Boolean> = _next
    var map = mutableMapOf<Int, Train>()
    var a = mutableMapOf<Int, String>()
    var myA = mutableMapOf<Int, String>()
//    val point = mutableMapOf<Int, Int>()


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
            setTrainSelected(trainQuestion.trains[0])
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
        _correctnessText.value = "SOAL $number DARI ${map.size} SOAL"
    }


    fun next() {
        if (_number.value!! < map.size) {
            setNumber(_number.value!!.plus(1))
            setTrainSelected(map[_number.value]!!)
        }
        if (_number.value!! >= map.size) {
            _finish.value = true
//            point.values.sum()
//            _points.value = point.values.sum().toString()
//            Log.e("TEMPIK", "POINT : ${point.values.sum()}")
        }
    }

//    fun updateScore(isTrue: Boolean, position: Int) {
//
//        if (isTrue) {
//            point[position] = 1
//        } else {
//            point[position] = 0
//        }
//        _score.value = point[position]
//        Log.e("TEMPIK", "TEMP : ${_score.value}")
//    }


    fun uploadImage(bitmap: Bitmap, fileNameToSave: String = "image") {
        _correctness.value = "PROCESSING..."
        viewModelScope.launch(Dispatchers.IO) {

            val file: File?
            file = File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()


            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            val service = ApiConfig.getApiCloud().predictHuruf(imageMultipart)

            service.enqueue(object : Callback<Predict> {
                override fun onResponse(
                    call: Call<Predict>,
                    response: Response<Predict>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody?.resultPredict == trainSelected.value?.answer) {
//                            updateScore(true, _number.value!!)
                            Log.e("TEMPIK", "onResponse: BNEAER")
                            _next.value = true
                            _correctness.value = "BENAR"
                            myA[_number.value!!] = responseBody?.resultPredict.toString()
                        } else {
//                            updateScore(false, _number.value!!)
                            Log.e("TEMPIK", "onResponse: SALAH")
                            _next.value = false
                            _correctness.value = "SALAH"
                            myA[_number.value!!] = responseBody?.resultPredict.toString()
                        }
                    }
                }

                override fun onFailure(call: Call<Predict>, t: Throwable) {
                    Log.e("ERROR_LOG", "onResponse: ${t.message}")
                }
            })

        }

    }

    fun lightGreen(resources: Resources): ColorStateList =
        ColorStateList.valueOf(resources.getColor(R.color.light_green))

    fun ultraLightPink(resources: Resources): ColorStateList =
        ColorStateList.valueOf(resources.getColor(R.color.ultra_light_pink))

    fun lightBlue(resources: Resources): ColorStateList =
        ColorStateList.valueOf(resources.getColor(R.color.light_blue))

}