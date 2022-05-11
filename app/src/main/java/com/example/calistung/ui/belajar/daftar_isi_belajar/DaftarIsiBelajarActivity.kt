package com.example.calistung.ui.belajar.daftar_isi_belajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calistung.adapter.ListLearnCourseAdapter
import com.example.calistung.databinding.ActivityDaftarBelajarBinding
import com.example.calistung.model.LearnCourse

class DaftarIsiBelajarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBelajarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDaftarBelajarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setContentView(binding.root)
        val item=intent.getParcelableExtra<LearnCourse>(LEARN_COURSE_SELECTED)
        val tes = item?.learns
        binding.rvGithub.apply {
            layoutManager = LinearLayoutManager(this@DaftarIsiBelajarActivity)
            adapter = ListLearnCourseAdapter(tes!!)
        }
    }

    /*private fun setUserData(userData: List<ItemsItem>) {
        val listReview = ArrayList<ItemsItem>()
        for (review in userData) {
            listReview.add(review)
        }
        val adapter = ListUserAdapter(listReview)
        activityMainBinding.rvGithub.adapter = adapter


    }*/

    /*private fun showRecycleView() {
        activityMainBinding.rvGithub.layoutManager = LinearLayoutManager(this)
    }*/
companion object{
      const val LEARN_COURSE_SELECTED="learn_course_selected"
}
}