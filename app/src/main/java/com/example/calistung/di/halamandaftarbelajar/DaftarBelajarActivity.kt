package com.example.calistung.di.halamandaftarbelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calistung.databinding.ActivityDaftarBelajarBinding

class DaftarBelajarActivity : AppCompatActivity() {
    private lateinit var daftarBelajarBinding: ActivityDaftarBelajarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        daftarBelajarBinding = ActivityDaftarBelajarBinding.inflate(layoutInflater)
        setContentView(daftarBelajarBinding.root)
    }
}