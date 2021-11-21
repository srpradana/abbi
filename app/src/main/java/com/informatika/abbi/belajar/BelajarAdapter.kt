package com.informatika.abbi.belajar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.informatika.abbi.data.BelajarData
import com.informatika.abbi.databinding.ItemGridListBinding

class BelajarAdapter(private val listData: ArrayList<BelajarData>): RecyclerView.Adapter<BelajarAdapter.BelajarBisindoViewHolder>() {

    inner class BelajarBisindoViewHolder(val binding: ItemGridListBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BelajarBisindoViewHolder {
        val view = ItemGridListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BelajarBisindoViewHolder(view)
    }

    override fun onBindViewHolder(holder: BelajarBisindoViewHolder, position: Int) {
        val bisindo = listData[position]

        Glide.with(holder.itemView.context)
            .load(bisindo.images)
            .into(holder.binding.imgItem)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}