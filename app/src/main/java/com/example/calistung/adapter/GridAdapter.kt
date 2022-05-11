package com.example.calistung.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calistung.databinding.ItemGridBinding
import com.example.calistung.model.Learn

class GridAdapter (
    private val items: ArrayList<Learn>
) :
    RecyclerView.Adapter<GridAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemGridBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val learn = items[position]
        holder.binding.apply {
            tvChar.text=learn.answer
            btnCoba.setOnClickListener {
                val context = it.context
                val intent = Intent(context, LearnActivity::class.java)
                intent.putExtra(LearnActivity.ITEM_SELECTED, learn)
                context.startActivity(intent)
            }
        }

    }
    override fun getItemCount(): Int = items.size

}