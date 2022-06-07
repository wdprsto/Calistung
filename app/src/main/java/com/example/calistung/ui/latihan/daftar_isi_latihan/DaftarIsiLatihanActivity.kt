package com.example.calistung.ui.latihan.daftar_isi_latihan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calistung.adapter.ListTrainCourseAdapter
import com.example.calistung.databinding.ActivityDaftarIsiLatihanBinding
import com.example.calistung.model.TrainCourse

class DaftarIsiLatihanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarIsiLatihanBinding
    private val model: DaftarIsiLatihanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarIsiLatihanBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        val item =
            intent.getParcelableExtra<TrainCourse>(TRAIN_COURSE_SELECTED)
        if (item != null)
            model.setTrainCourses(item)
        model.trainCourses.observe(this) {
            binding.rvGithub.apply {
                layoutManager = LinearLayoutManager(this@DaftarIsiLatihanActivity)
                adapter = ListTrainCourseAdapter(it.trainQuestion!!)
            }
            supportActionBar?.title=it.name
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    companion object {
        const val TRAIN_COURSE_SELECTED = "train_course_selected"
    }
}