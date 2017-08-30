package com.natthawut.tamboon.ui


import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.natthawut.tamboon.R
import com.natthawut.tamboon.databinding.CharitiesFragmentBinding
import com.natthawut.tamboon.injection.AppModules

/**
 * A simple [Fragment] subclass.
 */
class CharitiesFragment : LifecycleFragment() {

    private lateinit var binding: CharitiesFragmentBinding
    private lateinit var viewModel: CharitiesViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.charities_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = CharitiesViewModelFactory(AppModules.provideRepository())
        viewModel = ViewModelProviders.of(this, factory)
                .get(CharitiesViewModel::class.java)

        viewModel.retriveCharities()

        subscribeUi()
    }

    private fun subscribeUi() {
        // TODO()
    }

}// Required empty public constructor
