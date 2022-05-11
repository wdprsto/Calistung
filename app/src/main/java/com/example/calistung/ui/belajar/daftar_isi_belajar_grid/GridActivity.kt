package com.example.calistung.ui.belajar.daftar_isi_belajar_grid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calistung.adapter.GridAdapter
import com.example.calistung.databinding.ActivityGridBinding
import com.example.calistung.model.LearnCourse

class GridActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGridBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        val item=intent.getParcelableExtra<LearnCourse>(LEARN_COURSE_SELECTED)
        val tes = item?.learns
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@GridActivity, 3)
            adapter = GridAdapter(tes!!)
        }
    }
    companion object{
        const val LEARN_COURSE_SELECTED="learn_course_selected"
    }
}