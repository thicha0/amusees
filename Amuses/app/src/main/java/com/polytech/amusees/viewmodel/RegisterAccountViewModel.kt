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
import androidx.room.TypeConverter
import java.security.MessageDigest


class RegisterAccountViewModel(
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
        Log.i("RegisterAccountVM", "created")
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

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = null
    }

    //end register
    private val _navigateToLoginFragment = MutableLiveData<User>()

    val navigateToLoginFragment: LiveData<User>
        get() = _navigateToLoginFragment


    fun onValidateAccount() {
        Log.i("200","Click account")
        uiScope.launch {
            val user = user.value ?: return@launch

            if(user.email.isNullOrEmpty()){ // TODO format
                _alert.value = "Veuillez saisir votre email"
                return@launch
            }

            if(!formatEmailOK()) {
                _alert.value = "Veuillez saisir un email correct (exemple@example.org)"
                return@launch
            }

            //TODO Test identifiant disponible
            if(user.login.isNullOrEmpty()){
                _alert.value = "Veuillez saisir votre identifiant"
                return@launch
            }

            //TODO encoder le mdp + vérif
            if(user.password.isNullOrEmpty()) {
                _alert.value = "Veuillez saisir votre mot de passe"
                return@launch
            }
            encodePassword()

            update(user)

            _navigateToLoginFragment.value = user
        }
    }

    fun formatEmailOK(): Boolean {
        val email = user.value?.email+""
        val regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
        return email.matches(regex.toRegex())
    }

    fun encodePassword() {
        user.value?.password = MessageDigest
            .getInstance("MD5")
            .digest((user.value?.password+"").toByteArray()).toString()
    }

    fun doneNavigating() {
        _navigateToLoginFragment.value = null
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
        Log.i("RegisterAccountVM", "destroyed")
        viewModelJob.cancel()
    }
}