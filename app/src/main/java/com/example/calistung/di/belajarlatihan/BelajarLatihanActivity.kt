package com.example.calistung.di.belajarlatihan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calistung.databinding.ActivityDaftarBelajarBinding

class BelajarLatihanActivity : AppCompatActivity() {
    private lateinit var belajarBinding: ActivityDaftarBelajarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        belajarBinding = ActivityDaftarBelajarBinding.inflate(layoutInflater)
        setContentView(belajarBinding.root)
    }
}