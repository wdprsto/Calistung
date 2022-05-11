package com.example.calistung.ui.halamandaftarbelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calistung.databinding.ActivityDaftarBelajarBinding

class DaftarBelajarActivity : AppCompatActivity() {
    private lateinit var daftarBelajarBinding: ActivityDaftarBelajarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        daftarBelajarBinding = ActivityDaftarBelajarBinding.inflate(layoutInflater)
        setContentView(daftarBelajarBinding.root)
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