package com.natthawut.tamboon.ui.charities

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.natthawut.tamboon.remote.Charity
import com.natthawut.tamboon.repository.TamboonRepository

class CharitiesViewModel(private val repository: TamboonRepository) : ViewModel() {

    val charitiesLiveData = MutableLiveData<List<Charity>>()
    val errorMessageLiveData = MutableLiveData<String>()

    fun retrieveCharities() {
        repository.getOrganizations().subscribe(
                { charities -> charitiesLiveData.value = charities },
                { error -> errorMessageLiveData.value = error.message })

    }

}
