package com.natthawut.tamboon.ui.charities


import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.natthawut.tamboon.MainActivity
import com.natthawut.tamboon.R
import com.natthawut.tamboon.databinding.CharitiesFragmentBinding
import com.natthawut.tamboon.injection.AppModules
import com.natthawut.tamboon.remote.Charity
import com.natthawut.tamboon.ui.donation.DonationFragment

/**
 * A simple [Fragment] subclass.
 */
class CharitiesFragment : LifecycleFragment() {

    private lateinit var binding: CharitiesFragmentBinding
    private lateinit var viewModel: CharitiesViewModel

    private val clickListener = object : OnClickListener<Charity> {
        override fun onClick(data: Charity) {
            val donationFragment = DonationFragment.newInstance(data.name!!)
            ( activity as MainActivity).addBackStackFragment(donationFragment)
        }
    }

    private val adapter = CharitiesAdapter(clickListener)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.charities_fragment, container, false)
        binding.charityList.layoutManager = GridLayoutManager(context, 1)
        binding.charityList.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = CharitiesViewModelFactory(AppModules.provideRepository())
        viewModel = ViewModelProviders.of(this, factory)
                .get(CharitiesViewModel::class.java)

        viewModel.retrieveCharities()

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.charitiesLiveData.observe(this, Observer { charities ->
            adapter.charities = charities
        })
    }

}// Required empty public constructor
