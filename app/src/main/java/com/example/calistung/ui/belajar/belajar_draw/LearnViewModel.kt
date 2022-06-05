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


    var file: File? = null

    init {
        _correctness.value = false
        _isStarted.value = false
        uploadImage()
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
    /*fun setCorrectness(bitmap: Bitmap) {

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
    }*/

    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String = "image"): File? { // File name like "image.png"
        //create a file to write bitmap data

        return try {
            file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
            file?.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }


    private fun uploadImage() {
        viewModelScope.launch(Dispatchers.Default) {


            //Convert bitmap to byte array


            //write the bytes in file




            val requestImageFile = file?.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file?.name,
                requestImageFile!!
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
                            /*  _change.value = true
                              _isLoading.value = false
                              _toast.value = Event("berhasil")*/

                        }

                    } else {
                        _correctnessText.value = "SALAH"
                        /* _toast.value = Event("file_besar")
                         _change.value = false
                         _isLoading.value = false
                         Log.e(TAG, "onResponse: ${response.message()}")*/
                    }
                }

                override fun onFailure(call: Call<Predict>, t: Throwable) {
                    /*  if (t.message.equals("timeout")) {
                          _toast.value = Event("timeout")
                      } else {
                          _toast.value = Event("gagal")
                      }
                      _change.value = false
                      _isLoading.value = false*/
                    Log.e("TEMPIK", "onResponse: ${t.message}")
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

}