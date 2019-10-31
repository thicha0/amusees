package com.polytech.amusees.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.viewmodel.ListMuseesViewModel

class ListMuseesViewModelFactory (
    private val dataSource: UserDao,
    private val application: Application,
    private val userID: Long = 0L // userID
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListMuseesViewModel::class.java)) {
            return ListMuseesViewModel(dataSource, application,userID) as T // userID
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}