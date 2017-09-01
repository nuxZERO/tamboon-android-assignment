package com.natthawut.tamboon.ui.donation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.natthawut.tamboon.repository.TamboonRepository

@Suppress("UNCHECKED_CAST")
class DonationViewModelFactory(private val repository: TamboonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        if (modelClass!!.isAssignableFrom(DonationViewModel::class.java)) {
            return DonationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}
