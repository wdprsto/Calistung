@file:Suppress("PrivatePropertyName")

package com.example.calistung.ui.menu

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.calistung.databinding.ActivityMenuBinding
import com.example.calistung.repository.ResponseRepository
import com.example.calistung.service.ApiConfig
import com.example.calistung.ui.belajarlatihan.BelajarLatihanActivity
import com.example.calistung.ui.splashscreen.SplashScreenActivity
import com.example.calistung.utils.ViewModelFactory
import kotlinx.coroutines.runBlocking

class MenuPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var model: MenuPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        verifyStoragePermission(this)
        model = ViewModelProvider(
            this,
            ViewModelFactory(
                ResponseRepository(
                    ApiConfig.getApiService()
                )
            )
        )[MenuPageViewModel::class.java]
        try {
            runBlocking {
                model.getAllData().observe(this@MenuPageActivity) { response ->
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
        } catch (e: Exception) {
            binding.llButtons.visibility = View.GONE
            binding.llNoInternet.visibility = View.VISIBLE
            binding.btnReopen.setOnClickListener {
                val thisActivity = Intent(this, SplashScreenActivity::class.java)
                thisActivity.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(thisActivity)
            }
        }
    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSION_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun verifyStoragePermission(activity: Activity?) {
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                PERMISSION_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    companion object {
        const val CATEGORY_SELECTED = "category_selected"
    }
}