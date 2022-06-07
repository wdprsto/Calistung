package com.example.calistung.adapter

import android.content.Context
import android.content.Intent
import com.example.calistung.model.TrainCourse
import com.example.calistung.ui.latihan.daftar_isi_latihan.DaftarIsiLatihanActivity

class ListTrainAdapter(items: ArrayList<TrainCourse>) : ListAdapter<TrainCourse>(items) {
    override fun applyText(item: TrainCourse): String {
        return item.name.toString()
    }

    override fun clicked(item: TrainCourse, context: Context) {
        val intent = Intent(context, DaftarIsiLatihanActivity::class.java)
        intent.putExtra(DaftarIsiLatihanActivity.TRAIN_COURSE_SELECTED, item)
        context.startActivity(intent)

    }
}