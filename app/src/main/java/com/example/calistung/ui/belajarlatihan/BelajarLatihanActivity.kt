package com.example.calistung.ui.belajarlatihan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.calistung.databinding.ActivityBelajarLatihanBinding
import com.example.calistung.model.Category
import com.example.calistung.ui.belajar.daftar_belajar.DaftarBelajarActivity
import com.example.calistung.ui.latihan.daftar_latihan.DaftarLatihanActivity
import com.example.calistung.ui.menu.MenuPageActivity

class BelajarLatihanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBelajarLatihanBinding
    private val model: BelajarLatihanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelajarLatihanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<Category>(MenuPageActivity.CATEGORY_SELECTED)
        supportActionBar?.title = item?.name
            if (item != null)
            model.setCategory(item)
        model.category.observe(this) { category ->
            binding.apply {
                btnToBelajar.setOnClickListener {
                    val intent =
                        Intent(this@BelajarLatihanActivity, DaftarBelajarActivity::class.java)
                    intent.putExtra(
                        DaftarBelajarActivity.LEARN_COURSE_SELECTED,
                        category!!.learnCategories
                    )
                    startActivity(intent)
                }
                btnToLatihan.setOnClickListener {
                    val intent =
                        Intent(this@BelajarLatihanActivity, DaftarLatihanActivity::class.java)
                    intent.putExtra(
                        DaftarLatihanActivity.TRAIN_COURSE_SELECTED,
                        category!!.trainCategories
                    )
                    startActivity(intent)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}