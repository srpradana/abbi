package com.informatika.abbi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.informatika.abbi.belajar.BelajarActivity
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

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cv_belajar -> {
                val fragmentManager = activity?.supportFragmentManager
                val belajarMenuFragment = BelajarMenuFragment()

                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container,belajarMenuFragment, BelajarMenuFragment::class.java.simpleName)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}