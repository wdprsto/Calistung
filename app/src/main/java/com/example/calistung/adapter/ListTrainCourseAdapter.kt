package com.example.calistung.adapter

import android.content.Context
import android.content.Intent
import com.example.calistung.model.Train
import com.example.calistung.model.TrainCourse
import com.example.calistung.model.TrainQuestion
import com.example.calistung.ui.belajar.belajar_draw.LearnActivity
import com.example.calistung.ui.daftarisibelajar.ListAdapter
import com.example.calistung.ui.latihan.latihan_draw.TrainActivity

class ListTrainCourseAdapter(items: ArrayList<TrainQuestion>) : ListAdapter<TrainQuestion>(items) {
    override fun applyText(item: TrainQuestion): String {
        return item.name.toString()
    }

    override fun clicked(item: TrainQuestion, context: Context) {
        val intent = Intent(context, TrainActivity::class.java)
        intent.putExtra(TrainActivity.ITEM_SELECTED, item)
        context.startActivity(intent)
    }
}