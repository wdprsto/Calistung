package com.example.calistung.ui.belajar.daftar_belajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calistung.adapter.ListLearnAdapter
import com.example.calistung.databinding.ActivityDaftarBelajarBinding
import com.example.calistung.model.Learn
import com.example.calistung.model.LearnCourse

class DaftarBelajarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBelajarBinding
    private val learnCourse=LearnCourse("Belajar Alphabet", arrayListOf(
        Learn("belajar huruf A", "https://i.makeagif.com/media/8-16-2020/mDO6C4.gif", "A"),
        Learn("belajar huruf A", "https://i.makeagif.com/media/8-16-2020/mDO6C4.gif", "A"),
        Learn("belajar huruf A", "https://i.makeagif.com/media/8-16-2020/mDO6C4.gif", "A"),
    ))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBelajarBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        binding.rvGithub.apply {
            layoutManager = LinearLayoutManager(this@DaftarBelajarActivity)
            adapter = ListLearnAdapter(arrayListOf(learnCourse))
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

}