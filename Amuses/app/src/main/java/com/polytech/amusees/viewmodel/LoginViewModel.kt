package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.User
import kotlinx.coroutines.*
import java.security.MessageDigest


class LoginViewModel(
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
        Log.i("LoginViewModel", "created")
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

    //log me in
    private val _navigateToListMuseesFragment = MutableLiveData<User>()

    val navigateToListMuseesFragment: LiveData<User>
        get() = _navigateToListMuseesFragment


    fun onValidateLogin() {
        Log.i("200","Click login")
        uiScope.launch {
            var user = user.value ?: return@launch

            if(user.login.isNullOrEmpty()) {
                _alert.value = "Veuillez saisir votre identifiant"
                return@launch
            }

            if(user.password.isNullOrEmpty()) {
                _alert.value = "Veuillez saisir votre mot de passe"
                return@launch
            }

            val id = testLogin()
            Log.i("ID",id.toString())
            if (id > 0) {
                _user.value?.id = id
                _navigateToListMuseesFragment.value = user
            }
            else {
                _alert.value = "L'identifant et le mot de passe ne correspondent pas"
                return@launch
            }
        }
    }

    //register
    private val _navigateToRegister = MutableLiveData<Boolean>()

    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister


    fun onCreateAccount() {
        Log.i("200","Click register")
        _navigateToRegister.value = true
    }

    fun doneNavigating() {
        _navigateToListMuseesFragment.value = null
        _navigateToRegister.value = false
    }

    private suspend fun testLogin(): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.testLogin(user.value?.login?:"",encode("SHA1",user.value?.password+""))
        }
        return id
    }

    fun encode(type:String, input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("LoginViewModel", "destroyed")
        viewModelJob.cancel()
    }
}