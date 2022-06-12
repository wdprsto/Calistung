package com.example.calistung.ui.belajar.belajar_draw

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.core.content.ContextCompat.getExternalFilesDirs
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calistung.R
import com.example.calistung.model.Learn
import com.example.calistung.model.Predict
import com.example.calistung.service.ApiConfig
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

class LearnViewModel : ViewModel() {

    private val _learn = MutableLiveData<Learn>()
    val learn
        get() = _learn

    private val _correctness = MutableLiveData<Boolean>()

    private val _isStarted = MutableLiveData<Boolean>()
    val isStarted
        get() = _isStarted

    private val _correctnessText = MutableLiveData<String>()
    val correctnessText
        get() = _correctnessText

    private val _tts = MutableLiveData<TextToSpeech>()
    val tts
        get() = _tts

    private val _labelColor = MutableLiveData<ColorStateList>()
    val labelColor
        get() = _labelColor

    init {
        _correctness.value = false
        _isStarted.value = false
    }

    fun uploadImage(bitmap: Bitmap, fileNameToSave: String = "image",resources: Resources,context: Context) {
        _correctnessText.value = "PROCESSING..."
        setLightBlue(resources)
        viewModelScope.launch(Dispatchers.IO) {
            val file: File?
            file = File(
                context.filesDir
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
                            setLightGreen(resources)
                        } else {
                            _correctnessText.value = "SALAH"
                            _correctness.value = false
                            setUltraLightPink(resources)
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

    fun setLightGreen(resources: Resources) {
        _labelColor.value = ColorStateList.valueOf(resources.getColor(R.color.light_green))
    }

    fun setUltraLightPink(resources: Resources) {
        _labelColor.value = ColorStateList.valueOf(resources.getColor(R.color.ultra_light_pink))
    }

    private fun setLightBlue(resources: Resources) {
        _labelColor.value = ColorStateList.valueOf(resources.getColor(R.color.light_blue))
    }

    fun lightBlue(resources: Resources): ColorStateList =
        ColorStateList.valueOf(resources.getColor(R.color.light_blue))
}