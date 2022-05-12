package com.example.calistung.utils

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.calistung.model.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.runBlocking

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}
fun getTextFromPhoto(bitmap: Bitmap):String{
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val image = InputImage.fromBitmap(bitmap, 0)
    var tempText=""
    runBlocking {
    val result = recognizer.process(image)
        .addOnSuccessListener { visionText ->
            // Task completed successfully
            // ...
            tempText=visionText.text
            Log.e("TEMPIK","HASIL : "+visionText.text)
        }
        .addOnFailureListener { e ->
            // Task failed with an exception
            // ...
            Log.e("TEMPIK", "EXEPTION : $e")
        }
    }
    Log.e("TEMPIK",tempText)
    return tempText
}
object Dummy {
    val learnCategories = LearnCategory(
        "Belajar Huruf",
        arrayListOf(
            LearnCourse(
                "Belajar Alphabet", arrayListOf(
                    Learn(
                        "belajar huruf A",
                        "https://i.makeagif.com/media/8-16-2020/mDO6C4.gif",
                        "A"
                    ),
                    Learn(
                        "belajar huruf A",
                        "https://i.makeagif.com/media/8-16-2020/mDO6C4.gif",
                        "A"
                    ),
                    Learn(
                        "belajar huruf A",
                        "https://i.makeagif.com/media/8-16-2020/mDO6C4.gif",
                        "A"
                    ),
                )
            )
        )
    )
    val trainCategories = TrainCategory(
        "Latihan Huruf",
        arrayListOf(
            TrainCourse(
                "Latihan Alphabet",
                arrayListOf(
                    TrainQuestion(
                        "number one",
                        arrayListOf(
                            Train("belajar huruf A", "A", "A"),
                            Train("belajar huruf B", "B", "B"),
                            Train("belajar huruf C", "C", "C"),
                        )
                    )
                )

            )
        )
    )
    val categoryHuruf = Category(
        "HURUF",
        learnCategories,
        trainCategories
    )
    val categoryAngka = Category(
        "ANGKA",
        learnCategories,
        trainCategories
    )
    val categoryHitung = Category(
        "HITUNG",
        learnCategories,
        trainCategories
    )
    val response = ResponseCategory(categoryHuruf, categoryAngka, categoryHitung)
}
