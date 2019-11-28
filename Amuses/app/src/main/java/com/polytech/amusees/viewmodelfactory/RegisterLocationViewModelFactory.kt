package com.polytech.amusees.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.User
import com.polytech.amusees.viewmodel.RegisterLocationViewModel

class RegisterLocationViewModelFactory (
    private val dataSource: UserDao,
    private val application: Application,
    private val user: User = User()
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterLocationViewModel::class.java)) {
            return RegisterLocationViewModel(dataSource, application,user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}