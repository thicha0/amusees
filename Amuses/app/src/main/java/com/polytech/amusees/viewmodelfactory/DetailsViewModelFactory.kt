package com.polytech.amusees.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.amusees.model.Musee
import com.polytech.amusees.viewmodel.DetailsViewModel

class DetailsViewModelFactory (
    private val application: Application,
    private val musee: Musee
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(application, musee) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}