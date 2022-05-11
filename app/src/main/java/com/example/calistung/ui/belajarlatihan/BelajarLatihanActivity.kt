package com.example.calistung.ui.belajarlatihan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calistung.databinding.ActivityBelajarLatihanBinding
import com.example.calistung.ui.halamandaftarbelajar.DaftarBelajarActivity

class BelajarLatihanActivity : AppCompatActivity() {
    private lateinit var belajarBinding: ActivityBelajarLatihanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        belajarBinding = ActivityBelajarLatihanBinding.inflate(layoutInflater)
        setContentView(belajarBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        belajarBinding.belajar.setOnClickListener {
            val intent = Intent(this@BelajarLatihanActivity, DaftarBelajarActivity::class.java)
            startActivity(intent)
        }
    }


}