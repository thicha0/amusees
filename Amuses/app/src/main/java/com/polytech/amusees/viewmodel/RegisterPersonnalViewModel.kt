package com.polytech.amusees.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.amusees.model.User
import kotlinx.coroutines.launch

class RegisterPersonnalViewModel(userParam: User) : ViewModel()
{
    init {
        Log.i("RegisterPersonnalVM", "created")
    }

    private val _user = MutableLiveData<User>(userParam)
    val user: LiveData<User>
        get() = _user

    fun onGender(gender: String) {
        user.value?.gender = gender
        Log.i("Gender changed to ", gender)
    }

    private val _navigateToRegisterLocationFragment = MutableLiveData<User>()
    val navigateToRegisterLocationFragment: LiveData<User>
        get() = _navigateToRegisterLocationFragment

    fun doneNavigating() {
        _navigateToRegisterLocationFragment.value = null
    }

    fun onValidate() {
        _navigateToRegisterLocationFragment.value = user.value
    }
}