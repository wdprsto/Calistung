package com.example.calistung.ui.belajarlatihan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calistung.databinding.ActivityBelajarLatihanBinding
import com.example.calistung.ui.belajar.daftar_belajar.DaftarBelajarActivity
import com.example.calistung.ui.latihan.daftar_latihan.DaftarLatihanActivity

class BelajarLatihanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBelajarLatihanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelajarLatihanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.apply {
            btnToBelajar.setOnClickListener {
                val intent = Intent(this@BelajarLatihanActivity, DaftarBelajarActivity::class.java)
                startActivity(intent)
            }
            btnToLatihan.setOnClickListener {
                val intent = Intent(this@BelajarLatihanActivity, DaftarLatihanActivity::class.java)
                startActivity(intent)
            }

        }

    }


}