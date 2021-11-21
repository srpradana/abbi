package com.informatika.abbi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.informatika.abbi.belajar.BelajarActivity
import com.informatika.abbi.belajar.BelajarActivity.Companion.BISINDO_CODE
import com.informatika.abbi.belajar.BelajarActivity.Companion.KEY_CODE
import com.informatika.abbi.belajar.BelajarActivity.Companion.SIBI_CODE
import com.informatika.abbi.databinding.FragmentBelajarMenuBinding

class BelajarMenuFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentBelajarMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBelajarMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvBisindo.setOnClickListener(this)
        binding.cvSibi.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cv_bisindo -> {
                val code = BISINDO_CODE
                val intent = Intent(activity, BelajarActivity::class.java)
                intent.putExtra(KEY_CODE, code)
                startActivity(intent)
            }
            R.id.cv_sibi -> {
                val code = SIBI_CODE
                val intent = Intent(activity, BelajarActivity::class.java)
                intent.putExtra(KEY_CODE, code)
                startActivity(intent)
            }
        }
    }
}