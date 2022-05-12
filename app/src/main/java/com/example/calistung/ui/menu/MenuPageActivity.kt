package com.example.calistung.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.calistung.databinding.ActivityMenuBinding
import com.example.calistung.repository.ResponseRepository
import com.example.calistung.service.ApiConfig
import com.example.calistung.ui.belajarlatihan.BelajarLatihanActivity
import com.example.calistung.utils.Dummy
import com.example.calistung.utils.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

class MenuPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var model: MenuPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //model.setResponseCategory(Dummy.response)
        model = ViewModelProvider(
            this,
            ViewModelFactory(
                ResponseRepository(
                    ApiConfig.getApiService()
                )
            )
        )[MenuPageViewModel::class.java]
        runBlocking {
            model.getAllData().observe(this@MenuPageActivity) { response ->
//            Log.e("TEMPIK",Gson().toJson(response))
                binding.apply {
                    huruf.setOnClickListener {
                        val intent =
                            Intent(this@MenuPageActivity, BelajarLatihanActivity::class.java)
                        intent.putExtra(CATEGORY_SELECTED, response.huruf)
                        startActivity(intent)
                    }
                    angka.setOnClickListener {
                        val intent =
                            Intent(this@MenuPageActivity, BelajarLatihanActivity::class.java)
                        intent.putExtra(CATEGORY_SELECTED, response.angka)
                        startActivity(intent)
                    }
                    menghitung.setOnClickListener {
                        val intent =
                            Intent(this@MenuPageActivity, BelajarLatihanActivity::class.java)
                        intent.putExtra(CATEGORY_SELECTED, response.hitung)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    companion object {
        const val CATEGORY_SELECTED = "category_selected"
    }
}