package com.natthawut.tamboon.ui.donation

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.omise.android.TokenRequest
import com.natthawut.tamboon.R
import com.natthawut.tamboon.databinding.DonationFragmentBinding
import com.natthawut.tamboon.injection.AppModules

class DonationFragment : LifecycleFragment() {

    private var charityName: String? = null
    private lateinit var binding: DonationFragmentBinding

    private var viewModel: DonationViewModel? = null

    private var mListener: OnFragmentInteractionListener? = null

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
//        binding.cardNumberInput.setText("4242424242424242")
//        binding.nameOnCardInput.setText("JOHN DOE")
//        binding.expireMonthInput.setText("12")
//        binding.expireYearInput.setText("20")
//        binding.securityCodeInput.setText("123")
//        binding.amountInput.setText("10000")

        binding.donateClickListener = object : DonateClickListener {
            override fun onClick() {
                val tokenRequest = TokenRequest()
                tokenRequest.name = binding.nameOnCardInput.text.toString()
                tokenRequest.number = binding.cardNumberInput.text.toString()
                tokenRequest.expirationMonth = binding.expireMonthInput.text.toString().toInt()
                tokenRequest.expirationYear = 2000 + binding.expireYearInput.text.toString().toInt()
                tokenRequest.securityCode = binding.securityCodeInput.text.toString()

                val amount = binding.amountInput.text.toString().toInt()
                viewModel?.donate(tokenRequest, amount)

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
        viewModel?.donateResponseLiveData?.observe(this, Observer { result ->
            Toast.makeText(context, "${result?.success}", Toast.LENGTH_SHORT).show()

            binding.isProcessing = false
            showDonateCompletedDialog()
        })
    }

    private fun showDonateCompletedDialog() {
        AlertDialog.Builder(context)
                .setMessage(R.string.donate_completed)
                .setPositiveButton(R.string.back_to_charities_list, null)
                .show()
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

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
}// Required empty public constructor
