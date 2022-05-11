package com.example.calistung.ui.latihan.daftar_isi_latihan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calistung.adapter.ListTrainCourseAdapter
import com.example.calistung.databinding.ActivityDaftarIsiLatihanBinding
import com.example.calistung.model.TrainCourse

class DaftarIsiLatihanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDaftarIsiLatihanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDaftarIsiLatihanBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setContentView(binding.root)
        val item=intent.getParcelableExtra<TrainCourse>(DaftarIsiLatihanActivity.LEARN_COURSE_SELECTED)
        val tes = item?.trains
        binding.rvGithub.apply {
            layoutManager = LinearLayoutManager(this@DaftarIsiLatihanActivity)
            adapter = ListTrainCourseAdapter(tes!!)
        }
    }
    companion object{
        const val LEARN_COURSE_SELECTED="learn_course_selected"
    }
}