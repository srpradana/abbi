package com.informatika.abbi.belajar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.informatika.abbi.data.BelajarData
import com.informatika.abbi.data.BelajarAsetData
import com.informatika.abbi.databinding.ActivityBelajarBinding

class BelajarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBelajarBinding
    private lateinit var adapter: BelajarAdapter

    private var list : ArrayList<BelajarData> = arrayListOf()

    companion object{
        const val BISINDO_CODE = "123"
        const val SIBI_CODE = "111"
        const val KEY_CODE = "key_code"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelajarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when(intent.getStringExtra(KEY_CODE)){
            BISINDO_CODE -> list.addAll(BelajarAsetData.listDataBisindo)
            SIBI_CODE -> list.addAll(BelajarAsetData.listDataSibi)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        with(binding){
            rvBelajar.setHasFixedSize(true)
            rvBelajar.layoutManager = GridLayoutManager(this@BelajarActivity, 3)
            adapter = BelajarAdapter(list)
            rvBelajar.adapter = adapter
        }
    }
}