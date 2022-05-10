package com.example.calistung.di.daftarisibelajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calistung.databinding.ActivityDaftarBelajarBinding

class DaftarIsiBelajarActivity : AppCompatActivity() {
    private lateinit var daftarBelajarBinding: ActivityDaftarBelajarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        daftarBelajarBinding = ActivityDaftarBelajarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(daftarBelajarBinding.root)
    }
}