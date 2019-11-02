package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.User
import kotlinx.coroutines.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class RegisterPersonnalViewModel(
    val database: UserDao,
    application: Application,
    private val userID: Long = 0L // userID
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
            _user.value = getUserFromDatabase()
        }
    }

    private suspend fun getUserFromDatabase(): User? {
        return withContext(Dispatchers.IO) {

            var user = database.get(userID) // userID
            if (user == null) {
                user = User()
                user.id = insert(user)
            }
            user
        }
    }

    private suspend fun insert(user: User): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.insert(user)
        }
        return id
    }

    fun onGender(gender: String) {
        user.value?.gender = gender
        Log.i("Gender changed to ", gender)
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

            //TODO meilleur test (si c'est bien une date, si elle n'est pas aberrante)
            if(user.birthdayDate == 0L) {
                _alert.value = "Veuillez sélectionner votre date de naissance"
                return@launch
            }

            update(user)

            _navigateToRegisterLocationFragment.value = user
        }
    }

    fun doneNavigating() {
        _navigateToRegisterLocationFragment.value = null
    }

    private suspend fun update(user: User) {
        withContext(Dispatchers.IO) {
            database.update(user)
        }
    }

    private suspend fun get(id: Long): User? {
        return withContext(Dispatchers.IO) {
            database.get(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("RegisterPersonnalVM", "destroyed")
        viewModelJob.cancel()
    }
}