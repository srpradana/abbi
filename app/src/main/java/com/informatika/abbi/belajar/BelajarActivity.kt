package com.informatika.abbi.belajar

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.informatika.abbi.data.BelajarData
import com.informatika.abbi.data.BelajarAsetData
import com.informatika.abbi.databinding.ActivityBelajarBinding
import com.informatika.abbi.utils.StatePreference

class BelajarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBelajarBinding
    private lateinit var gridAdapter: BelajarGridAdapter
    private lateinit var fullAdapter: BelajarFullAdapter
    private var stateSwitch: Boolean = false

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
            BISINDO_CODE -> {
                title = "BISINDO"
                list.addAll(BelajarAsetData.listDataBisindo)
            }
            SIBI_CODE -> {
                title = "SIBI"
                list.addAll(BelajarAsetData.listDataSibi)
            }
        }

        showPreference()

        binding.swMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.rvBelajar.visibility = View.GONE
                binding.vpFullView.visibility = View.VISIBLE
                val statePreference = StatePreference(this)
                statePreference.setStateSwitch(binding.swMode.isChecked)
                prepareViewPager()
            }else{
                binding.vpFullView.visibility = View.GONE
                binding.rvBelajar.visibility = View.VISIBLE
                val statePreference = StatePreference(this)
                statePreference.setStateSwitch(binding.swMode.isChecked)
                setUpGridRecyclerView()
            }
        }
    }

    private fun showPreference() {
        stateSwitch = StatePreference(this).getStateSwitch()
        binding.swMode.isChecked = stateSwitch
    }

    private fun setUpFullRecyclerView(){
        with(binding){
            rvBelajar.setHasFixedSize(true)
            rvBelajar.layoutManager = LinearLayoutManager(this@BelajarActivity, RecyclerView.HORIZONTAL, false)
            fullAdapter = BelajarFullAdapter(list)
            rvBelajar.adapter = fullAdapter
        }
    }

    private fun prepareViewPager(){
        binding.vpFullView.adapter = BelajarFullAdapter(list)

    }

    private fun setUpGridRecyclerView() {
        with(binding){
            rvBelajar.setHasFixedSize(true)
            rvBelajar.layoutManager = GridLayoutManager(this@BelajarActivity, 3)
            gridAdapter = BelajarGridAdapter(list)
            rvBelajar.adapter = gridAdapter
        }
    }

}