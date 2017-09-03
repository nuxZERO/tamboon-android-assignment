package com.natthawut.tamboon.ui.charities

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.natthawut.tamboon.repository.remote.Charity
import com.natthawut.tamboon.repository.TamboonRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharitiesViewModel(private val repository: TamboonRepository) : ViewModel() {

    val charitiesLiveData = MutableLiveData<List<Charity>>()
    val errorMessageLiveData = MutableLiveData<String>()

    fun retrieveCharities() {
        repository.getCharities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { charities -> charitiesLiveData.value = charities },
                        { error -> errorMessageLiveData.value = error.message })

    }

}
