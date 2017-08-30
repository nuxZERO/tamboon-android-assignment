package com.natthawut.tamboon.ui.charities

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.natthawut.tamboon.remote.Charity
import com.natthawut.tamboon.repository.TamboonRepository

class CharitiesViewModel(private val repository: TamboonRepository) : ViewModel() {

    val charitiesLiveData = MutableLiveData<List<Charity>>()

    fun retrieveCharities() {
        repository.getOrganizations().subscribe { charities ->
            charitiesLiveData.value = charities
        }

    }

}
