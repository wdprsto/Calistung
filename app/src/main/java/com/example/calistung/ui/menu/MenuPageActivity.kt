@file:Suppress("PrivatePropertyName")

package com.example.calistung.ui.menu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         if(!allPermissionsGranted()){
             ActivityCompat.requestPermissions(
                 this,
                 PERMISSION_STORAGE,
                 REQUEST_EXTERNAL_STORAGE
             )
         }
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
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


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 500)
            }

        }
    }




    @RequiresApi(Build.VERSION_CODES.R)
    private fun allPermissionsGranted() = PERMISSION_STORAGE.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 1

        @RequiresApi(Build.VERSION_CODES.R)
        private val PERMISSION_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,


            )
        const val CATEGORY_SELECTED = "category_selected"
    }
}