package com.informatika.abbi.belajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.informatika.abbi.data.BelajarData
import com.informatika.abbi.data.BisindoBelajarData
import com.informatika.abbi.databinding.ActivityBelajarBinding

class BelajarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBelajarBinding
    private lateinit var adapter: BelajarBisindoAdapter

    private var list : ArrayList<BelajarData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelajarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list.addAll(BisindoBelajarData.listData)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        with(binding){
            rvBelajar.setHasFixedSize(true)
            rvBelajar.layoutManager = GridLayoutManager(this@BelajarActivity, 4)
            adapter = BelajarBisindoAdapter(list)
            rvBelajar.adapter = adapter
        }
    }
}