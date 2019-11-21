package com.polytech.amusees.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.Musee
import com.polytech.amusees.model.User
import com.polytech.amusees.service.Record
import com.polytech.amusees.viewmodel.DetailsViewModel
import com.polytech.amusees.viewmodel.RegisterLocationViewModel

class DetailsViewModelFactory (
    private val application: Application,
    private val musee: Musee
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(application, musee) as T // userID
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}