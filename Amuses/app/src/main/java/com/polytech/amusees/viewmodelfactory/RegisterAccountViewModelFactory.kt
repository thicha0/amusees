package com.polytech.amusees.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.User
import com.polytech.amusees.viewmodel.RegisterAccountViewModel

class RegisterAccountViewModelFactory (
    private val dataSource: UserDao,
    private val application: Application,
    private val user: User = User()
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterAccountViewModel::class.java)) {
            return RegisterAccountViewModel(dataSource, application,user) as T // userID
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}