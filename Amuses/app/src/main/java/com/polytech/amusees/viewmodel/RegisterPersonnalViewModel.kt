package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.User
import kotlinx.coroutines.*


class RegisterPersonnalViewModel(
    val database: UserDao,
    application: Application,
    private val myUser: User
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        Log.i("RegisterPersonnalVM", "created")
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = myUser
        }
    }

    fun onGender(gender: String) {
        user.value?.gender = gender
    }

    //alert
    private val _alert = MutableLiveData<String>()
    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = null
    }

    //goto Location
    private val _navigateToRegisterLocationFragment = MutableLiveData<User>()

    val navigateToRegisterLocationFragment: LiveData<User>
        get() = _navigateToRegisterLocationFragment

    fun onValidatePersonal() {
        Log.i("200","Click personal")
        uiScope.launch {
            val user = user.value ?: return@launch

            if(user.gender.isNullOrEmpty()) {//test si bien "Homme" ou "Femme"
                _alert.value = "Veuillez sélectionner votre genre"
                return@launch
            }

            if(user.lastname.isNullOrEmpty()) {//test si bien que des lettres (ou tirets ...)
                _alert.value = "Veuillez saisir votre nom"
                return@launch
            }

            if(user.firstname.isNullOrEmpty()) {//test si bien que des lettres (ou tirets ...)
                _alert.value = "Veuillez saisir votre prénom"
                return@launch
            }

            if(user.birthdayDate == 0L) {
                _alert.value = "Veuillez sélectionner votre date de naissance"
                return@launch
            }

            _navigateToRegisterLocationFragment.value = user
        }
    }

    fun doneNavigating() {
        _navigateToRegisterLocationFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("RegisterPersonnalVM", "destroyed")
        viewModelJob.cancel()
    }
}