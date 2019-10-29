package com.polytech.amusees.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polytech.amusees.model.User
import com.polytech.amusees.viewmodel.RegisterLocationViewModel

class RegisterLocationViewModelFactory(private val user: User) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterLocationViewModel::class.java)) {
            return RegisterLocationViewModel(user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}