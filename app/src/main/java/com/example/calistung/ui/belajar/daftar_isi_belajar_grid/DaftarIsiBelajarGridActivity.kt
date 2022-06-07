package com.example.calistung.ui.belajar.daftar_isi_belajar_grid

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calistung.adapter.GridAdapter
import com.example.calistung.databinding.ActivityDaftarIsiBelajarGridBinding
import com.example.calistung.model.LearnCourse
import com.example.calistung.ui.belajar.daftar_isi_belajar.DaftarIsiBelajarActivity

class DaftarIsiBelajarGridActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarIsiBelajarGridBinding
    private val model: DaftarIsiBelajarGridViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarIsiBelajarGridBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        val item =
            intent.getParcelableExtra<LearnCourse>(DaftarIsiBelajarActivity.LEARN_COURSE_SELECTED)
        if (item != null)
            model.setLearnCourses(item)
        model.learnCourses.observe(this) {
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(this@DaftarIsiBelajarGridActivity, 3)
                adapter = GridAdapter(it.learns!!)
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