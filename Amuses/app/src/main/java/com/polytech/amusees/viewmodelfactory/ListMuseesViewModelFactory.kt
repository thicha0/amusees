package com.polytech.amusees.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.Request
import com.polytech.amusees.viewmodel.ListMuseesViewModel


class ListMuseesViewModelFactory (
    private val application: Application,
    private val request: Request
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListMuseesViewModel::class.java)) {
            return ListMuseesViewModel(request) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}