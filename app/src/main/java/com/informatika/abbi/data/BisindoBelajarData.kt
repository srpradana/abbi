package com.informatika.abbi.data

import com.informatika.abbi.R

object BisindoBelajarData {

    private val bisindoBelajarImages = intArrayOf(
        R.drawable.bisindo_a,
        R.drawable.bisindo_b,
        R.drawable.bisindo_c,
        R.drawable.bisindo_d,
        R.drawable.bisindo_e,
        R.drawable.bisindo_f,
        R.drawable.bisindo_g,
        R.drawable.bisindo_h,
        R.drawable.bisindo_i,
        R.drawable.bisindo_j,
        R.drawable.bisindo_k,
        R.drawable.bisindo_l,
        R.drawable.bisindo_m,
    )

    val listData: ArrayList<BelajarData>
        get(){
            val list = arrayListOf<BelajarData>()
            for(position in bisindoBelajarImages.indices){
                val belajarData = BelajarData()
                belajarData.images = bisindoBelajarImages[position]
                list.add(belajarData)
            }
            return list
        }
}