package com.example.calistung.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calistung.databinding.ItemRowBinding
import kotlin.collections.ArrayList


abstract class ListAdapter<T>(private val items: ArrayList<T>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val binding =
                ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ListViewHolder(binding)
        }
        abstract fun applyText(item:T):String
        abstract fun clicked(item:T,context: Context)
        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val item = items[position]
            holder.apply {
                binding.tvName.text=applyText(item)
                itemView.setOnClickListener {
                    clicked(item,it.context)
                }
            }
        }

        override fun getItemCount(): Int = items.size



}
