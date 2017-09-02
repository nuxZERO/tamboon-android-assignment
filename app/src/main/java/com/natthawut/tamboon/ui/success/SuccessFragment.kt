package com.natthawut.tamboon.ui.success

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.natthawut.tamboon.R
import com.natthawut.tamboon.databinding.SuccessFragmentBinding

class SuccessFragment : Fragment() {

    private lateinit var binding: SuccessFragmentBinding

    private val successClickListener = object : SuccessClickListener {
        override fun onClick() {
            fragmentManager.popBackStack("Donation", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.success_fragment, container, false)
        binding.successClickListener = successClickListener
        return binding.root
    }

}
