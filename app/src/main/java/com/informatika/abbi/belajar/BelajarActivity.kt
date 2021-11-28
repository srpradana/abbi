package com.informatika.abbi.belajar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.informatika.abbi.R
import com.informatika.abbi.data.BelajarData
import com.informatika.abbi.data.BelajarAsetData
import com.informatika.abbi.databinding.ActivityBelajarBinding
import com.informatika.abbi.utils.StatePreference

class BelajarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBelajarBinding
    private lateinit var gridAdapter: BelajarGridAdapter
    private var stateSwitch: Boolean = false

    private var list : ArrayList<BelajarData> = arrayListOf()

    companion object{
        const val BISINDO_CODE = "123"
        const val SIBI_CODE = "111"
        const val KEY_CODE = "key_code"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelajarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.swMode.text = "Mode Layar Penuh"

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
        firstLayoutRecyclerView(stateSwitch)

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

    private fun prepareViewPager(){
        binding.vpFullView.adapter = BelajarFullAdapter(list)

    }

    private fun setUpGridRecyclerView() {
        with(binding){
            rvBelajar.layoutManager = GridLayoutManager(this@BelajarActivity, 3)
            gridAdapter = BelajarGridAdapter(list)
            rvBelajar.adapter = gridAdapter
        }
    }

    private fun firstLayoutRecyclerView(checked: Boolean){
        if(checked){
            binding.rvBelajar.visibility = View.GONE
            binding.vpFullView.visibility = View.VISIBLE
            prepareViewPager()
        }else{
            binding.vpFullView.visibility = View.GONE
            binding.rvBelajar.visibility = View.VISIBLE
            setUpGridRecyclerView()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}