package com.natthawut.tamboon.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.natthawut.tamboon.remote.Charity
import com.natthawut.tamboon.repository.TamboonRepository

class CharitiesViewModel(val repository: TamboonRepository) : ViewModel() {

    val charitiesLiveData  = MutableLiveData<List<Charity>>()

    fun retriveCharities() {
        charitiesLiveData.value = arrayListOf(Charity(), Charity())
    }

}
