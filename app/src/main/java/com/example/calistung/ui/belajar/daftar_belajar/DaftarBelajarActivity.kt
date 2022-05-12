package com.example.calistung.ui.belajar.daftar_belajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calistung.adapter.ListLearnAdapter
import com.example.calistung.databinding.ActivityDaftarBelajarBinding
import com.example.calistung.model.LearnCategory

class DaftarBelajarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBelajarBinding
    private val model: DaftarBelajarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBelajarBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        val item =
            intent.getParcelableExtra<LearnCategory>(LEARN_COURSE_SELECTED)
        if(item!=null)
            model.setLearnCategories(item)
        model.learnCategory.observe(this) {
            binding.rvGithub.apply {
                layoutManager = LinearLayoutManager(this@DaftarBelajarActivity)
                adapter = ListLearnAdapter(it.learnCourse!!)
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
        const val LEARN_COURSE_SELECTED = "learn_course_selected"
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