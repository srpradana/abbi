package com.informatika.abbi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.informatika.abbi.belajar.BelajarActivity
import com.informatika.abbi.berlatih.BerlatihActivity
import com.informatika.abbi.data.BerlatihData
import com.informatika.abbi.databinding.FragmentMenuBinding

class MenuFragment : Fragment(), View.OnClickListener {

    private var binding: FragmentMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.cvBelajar?.setOnClickListener(this)
        binding?.cvBerlatih?.setOnClickListener(this)
        binding?.cvTranslasi?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cv_belajar -> {
                Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_belajarMenuFragment)
            }
            R.id.cv_berlatih -> {
                val intent = Intent(activity, BerlatihActivity::class.java)
                startActivity(intent)
            }
            R.id.cv_translasi ->{
                val intent = Intent(activity, Translate::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}