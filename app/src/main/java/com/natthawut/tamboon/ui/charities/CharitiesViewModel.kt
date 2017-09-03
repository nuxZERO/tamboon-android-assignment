package com.natthawut.tamboon.ui.charities

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.natthawut.tamboon.repository.TamboonRepository
import com.natthawut.tamboon.repository.WrapperObserver
import com.natthawut.tamboon.repository.remote.Charity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharitiesViewModel(private val repository: TamboonRepository) : ViewModel() {

    val charitiesLiveData = MutableLiveData<List<Charity>>()
    val errorMessageLiveData = MutableLiveData<String>()

    fun retrieveCharities() {
        repository.getCharities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : WrapperObserver<List<Charity>>() {
                    override fun success(t: List<Charity>) {
                        charitiesLiveData.value = t
                        errorMessageLiveData.value = null
                    }

                    override fun failure(errorMessage: String?) {
                        errorMessageLiveData.value = errorMessage
                    }
                })
    }

}
