package com.example.calistung.ui.belajar.daftar_isi_belajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calistung.adapter.ListLearnCourseAdapter
import com.example.calistung.databinding.ActivityDaftarBelajarBinding
import com.example.calistung.model.LearnCourse

class DaftarIsiBelajarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBelajarBinding
    private val model:DaftarIsiBelajarViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDaftarBelajarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        val item = intent.getParcelableExtra<LearnCourse>(LEARN_COURSE_SELECTED)
        if(item!=null)
            model.setLearnCourses(item)
        model.learnCourses.observe(this){
            binding.rvGithub.apply {
                layoutManager = LinearLayoutManager(this@DaftarIsiBelajarActivity)
                adapter = ListLearnCourseAdapter(it.learns!!)
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
        const val LEARN_COURSE_SELECTED = "learn_course_selected"
    }
}