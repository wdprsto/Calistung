package com.example.calistung.di.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calistung.databinding.ActivityMenuBinding
import com.example.calistung.di.belajarlatihan.BelajarLatihanActivity

class menuPageActivity : AppCompatActivity() {
    private lateinit var menuBinding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuBinding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(menuBinding.root)

        menuBinding.huruf.setOnClickListener {
            val intent = Intent(this@menuPageActivity, BelajarLatihanActivity::class.java)
            startActivity(intent)
        }
    }
}