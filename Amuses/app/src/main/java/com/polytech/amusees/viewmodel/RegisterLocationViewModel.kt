package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.User
import kotlinx.coroutines.*



//TODO Bind spinner with enum
enum class Countries {
    ANDORRE,
    AFGHANISTAN,
    FRANCE
}

class RegisterLocationViewModel(
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
        Log.i("RegisterLocationVM", "created")
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = myUser
        }
    }

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = null
    }

    //goto account
    private val _navigateToRegisterAccountFragment = MutableLiveData<User>()

    val navigateToRegisterAccountFragment: LiveData<User>
        get() = _navigateToRegisterAccountFragment


    fun onValidateLocation() {
        Log.i("200","Click location")
        uiScope.launch {
            val user = user.value ?: return@launch

            if(user.adress.isNullOrEmpty()) { //TODO vérif format ?
                _alert.value = "Veuillez saisir votre adresse"
                return@launch
            }

            if(user.city.isNullOrEmpty()) {//test si bien que des lettres (ou tirets ...)
                _alert.value = "Veuillez saisir votre ville"
                return@launch
            }

            if(user.country.isNullOrEmpty()) { // TODO Spinner
                _alert.value = "Veuillez sélectionner votre pays"
                return@launch
            }

            _navigateToRegisterAccountFragment.value = user
        }
    }

    fun doneNavigating() {
        _navigateToRegisterAccountFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("RegisterLocationVM", "destroyed")
        viewModelJob.cancel()
    }
}