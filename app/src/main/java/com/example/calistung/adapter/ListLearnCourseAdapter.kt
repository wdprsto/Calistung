package com.example.calistung.adapter

import android.content.Context
import android.content.Intent
import com.example.calistung.model.Learn
import com.example.calistung.ui.belajar.belajar_draw.LearnActivity

class ListLearnCourseAdapter(items: ArrayList<Learn>) : ListAdapter<Learn>(items) {
    override fun applyText(item: Learn): String {
        return item.name.toString()
    }

    override fun clicked(item: Learn, context: Context) {
            val intent = Intent(context, LearnActivity::class.java)
            intent.putExtra(LearnActivity.ITEM_SELECTED, item)
            context.startActivity(intent)

    }
}