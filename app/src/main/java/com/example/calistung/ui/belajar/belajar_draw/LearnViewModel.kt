package com.example.calistung.ui.belajar.belajar_draw

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calistung.R
import com.example.calistung.model.Learn
import com.example.calistung.model.Predict
import com.example.calistung.service.ApiConfig
import com.example.calistung.utils.getTextFromPhoto
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
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

    fun uploadImage(bitmap: Bitmap, fileNameToSave: String = "image") {
        _correctnessText.value = "PROCESSING..."
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
//                        _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody?.resultPredict == _learn.value?.answer) {
                            _correctnessText.value = "BENAR"
                            _correctness.value = true
                        } else {
                            _correctnessText.value = "SALAH"
                            _correctness.value = false
                        }

                    }
                }

                override fun onFailure(call: Call<Predict>, t: Throwable) {
                    Log.e("ERROR_LOG", "onResponse: ${t.message}")
                }
            })

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

    fun lightBlue(resources: Resources): ColorStateList =
        ColorStateList.valueOf(resources.getColor(R.color.light_blue))

}