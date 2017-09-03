package com.natthawut.tamboon.ui.charities


import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.natthawut.tamboon.MainActivity
import com.natthawut.tamboon.R
import com.natthawut.tamboon.databinding.CharitiesFragmentBinding
import com.natthawut.tamboon.injection.AppModules
import com.natthawut.tamboon.repository.remote.Charity
import com.natthawut.tamboon.ui.donation.DonationFragment

class CharitiesFragment : LifecycleFragment() {

    private lateinit var binding: CharitiesFragmentBinding
    private lateinit var viewModel: CharitiesViewModel

    private val charityItemClickListener = object : OnClickListener<Charity> {
        override fun onClick(data: Charity) {
            val donationFragment = DonationFragment.newInstance(data.name!!)
            (activity as MainActivity).addBackStackFragment(donationFragment, "Donation")
        }
    }

    private val retryClickListener = object : RetryClickListener {
        override fun onRetryClick() {
            retrieveCharities()
        }
    }

    private val adapter = CharitiesAdapter(charityItemClickListener)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.charities_fragment, container, false)

        // Set layout manager and adapter to recyclerView
        binding.charityList.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.charities_column))
        binding.charityList.adapter = adapter

        // Set on retry click listener
        binding.retryClickListener = retryClickListener

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = CharitiesViewModelFactory(AppModules.provideRepository())
        viewModel = ViewModelProviders.of(this, factory)
                .get(CharitiesViewModel::class.java)

        // Init data in adapter
        adapter.charities = viewModel.charitiesLiveData.value

        retrieveCharities()

        subscribeUi()
    }

    private fun retrieveCharities() {

        // Retrieve charities list
        viewModel.retrieveCharities()

        if (adapter.charities == null) {
            binding.isShowProgressBar = true
        }

        binding.isShowErrorWithRetryButton = false
    }

    private fun subscribeUi() {
        viewModel.charitiesLiveData.observe(this, Observer { charities ->
            // Update charities list and update UI
            binding.isShowProgressBar = false
            adapter.charities = charities
        })

        viewModel.errorMessageLiveData.observe(this, Observer { errorMessage ->
            binding.isShowProgressBar = false
            showErrorMessage(errorMessage)
        })
    }

    private fun showErrorMessage(message: String?) {

        if (message == null) {
            return
        }

        // When charities list showing and get error message then show SnackBar
        if (adapter.charities != null) {
            Snackbar.make(binding.charitiesLayout, message, Snackbar.LENGTH_LONG)
                    .setAction(R.string.dismiss) { retrieveCharities() }
                    .show()
            binding.isShowErrorWithRetryButton = false
        } else {
            binding.isShowErrorWithRetryButton = true
            binding.errorMessage = message
        }
    }

}
