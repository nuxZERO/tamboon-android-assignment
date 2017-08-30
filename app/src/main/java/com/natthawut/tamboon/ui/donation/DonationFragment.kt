package com.natthawut.tamboon.ui.donation

import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.natthawut.tamboon.R
import com.natthawut.tamboon.databinding.DonationFragmentBinding

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DonationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DonationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonationFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var charityName: String? = null
    private lateinit var binding: DonationFragmentBinding

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
//        return inflater!!.inflate(R.layout.donation_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.donation_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.charityName = charityName
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_CHARITY_NAME = "charity_name"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param charityName Parameter 1.
         * @return A new instance of fragment DonationFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(charityName: String): DonationFragment {
            val fragment = DonationFragment()
            val args = Bundle()
            args.putString(ARG_CHARITY_NAME, charityName)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
