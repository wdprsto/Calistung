package com.example.calistung.ui.latihan.daftar_latihan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calistung.R
import com.example.calistung.adapter.ListLearnAdapter
import com.example.calistung.adapter.ListTrainAdapter
import com.example.calistung.databinding.ActivityDaftarIsiLatihanBinding
import com.example.calistung.model.Learn
import com.example.calistung.model.LearnCourse
import com.example.calistung.model.Train
import com.example.calistung.model.TrainCourse

class DaftarLatihanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDaftarIsiLatihanBinding
    private val trainCourse= TrainCourse("Belajar Alphabet", arrayListOf(
        Train("belajar huruf A", "A", "A"),
        Train("belajar huruf A", "A", "A"),
        Train("belajar huruf A", "A", "A"),
    ))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDaftarIsiLatihanBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        binding.rvGithub.apply {
            layoutManager = LinearLayoutManager(this@DaftarLatihanActivity)
            adapter = ListTrainAdapter(arrayListOf(trainCourse))
        }
    }
}