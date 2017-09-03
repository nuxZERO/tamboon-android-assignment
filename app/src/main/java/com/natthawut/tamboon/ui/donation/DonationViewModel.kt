package com.natthawut.tamboon.ui.donation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import co.omise.android.TokenRequest
import com.natthawut.tamboon.repository.remote.DonationResponse
import com.natthawut.tamboon.repository.TamboonRepository

class DonationViewModel(val repository: TamboonRepository) : ViewModel() {

    val donateResponseLiveData = MutableLiveData<DonationResponse>()
    val errorMessageLiveData = MutableLiveData<String>()

    fun donate(tokenRequest: TokenRequest, amount: Int) {
        repository.donate(tokenRequest, amount, { result, error ->
            if (result != null) {
                donateResponseLiveData.value = result
                errorMessageLiveData.value = null
            } else {
                error?.printStackTrace()
                donateResponseLiveData.value = null
                errorMessageLiveData.value = error?.message
            }
        })
    }

}
