package com.informatika.abbi.belajar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.informatika.abbi.data.BelajarData
import com.informatika.abbi.databinding.ItemFullviewListBinding

class BelajarFullAdapter(private val listData: ArrayList<BelajarData>): RecyclerView.Adapter<BelajarFullAdapter.BelajarFullViewHolder>() {

    inner class BelajarFullViewHolder(val binding: ItemFullviewListBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BelajarFullViewHolder {
        val view = ItemFullviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BelajarFullViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BelajarFullViewHolder, position: Int) {
        val dataBahasa = listData[position]

        Glide.with(holder.itemView)
            .load(dataBahasa.images)
            .into(holder.binding.imgItem)

        holder.binding.tvCount.text = "${position+1}/26"
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}