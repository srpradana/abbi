package com.informatika.abbi.data

import com.informatika.abbi.R

object BelajarAsetData {

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
        R.drawable.bisindo_n,
        R.drawable.bisindo_o,
        R.drawable.bisindo_p,
        R.drawable.bisindo_q,
        R.drawable.bisindo_r,
        R.drawable.bisindo_s,
        R.drawable.bisindo_t,
        R.drawable.bisindo_u,
        R.drawable.bisindo_v,
        R.drawable.bisindo_w,
        R.drawable.bisindo_x,
        R.drawable.bisindo_y,
        R.drawable.bisindo_z,
    )

    private val sibiBelajarImages = intArrayOf(
        R.drawable.sibi_a,
        R.drawable.sibi_b,
        R.drawable.sibi_c,
        R.drawable.sibi_d,
        R.drawable.sibi_e,
        R.drawable.sibi_f,
        R.drawable.sibi_g,
        R.drawable.sibi_h,
        R.drawable.sibi_i,
        R.drawable.sibi_j,
        R.drawable.sibi_k,
        R.drawable.sibi_l,
        R.drawable.sibi_m,
        R.drawable.sibi_n,
        R.drawable.sibi_o,
        R.drawable.sibi_p,
        R.drawable.sibi_q,
        R.drawable.sibi_r,
        R.drawable.sibi_s,
        R.drawable.sibi_t,
        R.drawable.sibi_u,
        R.drawable.sibi_v,
        R.drawable.sibi_w,
        R.drawable.sibi_x,
        R.drawable.sibi_y,
        R.drawable.sibi_z,
    )

    val listDataBisindo: ArrayList<BelajarData>
        get(){
            val list = arrayListOf<BelajarData>()
            for(position in bisindoBelajarImages.indices){
                val belajarData = BelajarData()
                belajarData.images = bisindoBelajarImages[position]
                list.add(belajarData)
            }
            return list
        }

    val listDataSibi: ArrayList<BelajarData>
        get(){
            val list = arrayListOf<BelajarData>()
            for(position in sibiBelajarImages.indices){
                val belajarData = BelajarData()
                belajarData.images = sibiBelajarImages[position]
                list.add(belajarData)
            }
            return list
        }
}