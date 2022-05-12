package com.example.calistung.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.calistung.model.*

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
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
