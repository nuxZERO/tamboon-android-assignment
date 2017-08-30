package com.natthawut.tamboon.ui.charities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.natthawut.tamboon.repository.TamboonRepository

@Suppress("UNCHECKED_CAST")
class CharitiesViewModelFactory(private val repository: TamboonRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        if (modelClass!!.isAssignableFrom(CharitiesViewModel::class.java)) {
            return CharitiesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}
