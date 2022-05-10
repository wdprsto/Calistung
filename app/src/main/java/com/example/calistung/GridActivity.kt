package com.example.calistung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calistung.adapter.GridAdapter
import com.example.calistung.databinding.ActivityGridBinding
import com.example.calistung.model.Learn

class GridActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGridBinding
    private val tes = arrayListOf(
        Learn("belajar huruf A", "https://i.makeagif.com/media/8-16-2020/mDO6C4.gif", "A"),
        Learn("belajar huruf A", "https://i.makeagif.com/media/8-16-2020/mDO6C4.gif", "A")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@GridActivity, 3)
            adapter = GridAdapter(tes)
        }
    }
}