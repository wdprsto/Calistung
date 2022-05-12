package com.example.calistung.ui.latihan.daftar_latihan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calistung.adapter.ListTrainAdapter
import com.example.calistung.databinding.ActivityDaftarIsiLatihanBinding
import com.example.calistung.model.*

class DaftarLatihanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarIsiLatihanBinding
    private val model: DaftarLatihanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarIsiLatihanBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        val item =
            intent.getParcelableExtra<TrainCategory>(TRAIN_COURSE_SELECTED)
        if (item != null)
            model.setTrainCategories(item)
        model.trainCategory.observe(this) {
            binding.rvGithub.apply {
                layoutManager = LinearLayoutManager(this@DaftarLatihanActivity)
                adapter = ListTrainAdapter(it.trainCourse!!)
            }
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