package com.example.calistung.adapter

import android.content.Context
import android.content.Intent
import com.example.calistung.model.LearnCourse
import com.example.calistung.ui.belajar.daftar_isi_belajar.DaftarIsiBelajarActivity
import com.example.calistung.ui.daftarisibelajar.ListAdapter
import com.example.calistung.ui.belajar.daftar_isi_belajar_grid.DaftarIsiBelajarGridActivity

class ListLearnAdapter(items: ArrayList<LearnCourse>) : ListAdapter<LearnCourse>(items) {
    override fun applyText(item: LearnCourse): String {
        return item.name.toString()
    }

    override fun clicked(item: LearnCourse, context: Context) {
        if(item.name=="Belajar Alfabet"||item.name=="Belajar Angka Dasar") {
            val intent = Intent(context, DaftarIsiBelajarGridActivity::class.java)
            intent.putExtra(DaftarIsiBelajarGridActivity.LEARN_COURSE_SELECTED, item)
            context.startActivity(intent)
        }else{
            val intent = Intent(context, DaftarIsiBelajarActivity::class.java)
            intent.putExtra(DaftarIsiBelajarActivity.LEARN_COURSE_SELECTED, item)
            context.startActivity(intent)
        }
    }
}