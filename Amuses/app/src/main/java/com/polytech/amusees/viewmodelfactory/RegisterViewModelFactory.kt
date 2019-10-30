package com.polytech.amusees.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.viewmodel.RegisterViewModel

class RegisterViewModelFactory (
    private val dataSource: UserDao,
    private val application: Application,
    private val userID: Long = 0L // userID
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(dataSource, application,userID) as T // userID
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}