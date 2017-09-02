package com.natthawut.tamboon.ui.donation

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import co.omise.android.TokenRequest
import com.natthawut.tamboon.MainActivity
import com.natthawut.tamboon.R
import com.natthawut.tamboon.databinding.DonationFragmentBinding
import com.natthawut.tamboon.injection.AppModules
import com.natthawut.tamboon.ui.success.SuccessFragment

class DonationFragment : LifecycleFragment() {

    private var charityName: String? = null
    private lateinit var binding: DonationFragmentBinding

    private var viewModel: DonationViewModel? = null

    companion object {

        private val ARG_CHARITY_NAME = "charity_name"

        fun newInstance(charityName: String): DonationFragment {
            val fragment = DonationFragment()
            val args = Bundle()
            args.putString(ARG_CHARITY_NAME, charityName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            charityName = arguments.getString(ARG_CHARITY_NAME)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.donation_fragment, container, false)

        // TODO: Delete hard code
        binding.cardNumberInput.setText("4242424242424242")
        binding.nameOnCardInput.setText("JOHN DOE")
        binding.expireMonthInput.setText("12")
        binding.expireYearInput.setText("20")
        binding.securityCodeInput.setText("123")
        binding.amountInput.setText("10000")

        binding.donateClickListener = object : DonateClickListener {
            override fun onClick() {

                if (!checkInputValidation()) {
                    return
                }

                // Get data from inputs
                val tokenRequest = TokenRequest()
                tokenRequest.name = binding.nameOnCardInput.text.toString()
                tokenRequest.number = binding.cardNumberInput.text.toString()
                tokenRequest.expirationMonth = binding.expireMonthInput.text.toString().toInt()
                tokenRequest.expirationYear = 2000 + binding.expireYearInput.text.toString().toInt()
                tokenRequest.securityCode = binding.securityCodeInput.text.toString()
                val amount = binding.amountInput.text.toString().toInt()

                // Send request to donate endpoint
                viewModel?.donate(tokenRequest, amount)

                // Show progrss bar
                binding.isProcessing = true

            }
        }

        binding.isProcessing = false

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.charityName = charityName

        val factory = DonationViewModelFactory(AppModules.provideRepository())
        viewModel = ViewModelProviders.of(this, factory)
                .get(DonationViewModel::class.java)

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel?.donateResponseLiveData?.observe(this, Observer { response ->
            binding.isProcessing = false
            if (response != null && response.success) {
                // Show success fragment
                (activity as MainActivity).addBackStackFragment(SuccessFragment(), "Success")
            }
        })

        viewModel?.errorMessageLiveData?.observe(this, Observer { errorMessage ->
            binding.isProcessing = false
            if (errorMessage != null) {
                // Show error message
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkInputValidation(): Boolean {
        mapOf(
                binding.nameOnCardInput to binding.nameOnCardInputLayout,
                binding.cardNumberInput to binding.cardNumberInputLayout,
                binding.expireMonthInput to binding.expireMonthInputLayout,
                binding.expireYearInput to binding.expireYearInputLayout,
                binding.securityCodeInput to binding.securityCodeInputLayout,
                binding.amountInput to binding.amountInputLayout
        ).forEach { (k, v) ->
            if (k.text.toString().isEmpty()) {
                v.error = getString(R.string.required_input)
                return false
            } else {
                v.error = null
            }
        }

        return true
    }

}
