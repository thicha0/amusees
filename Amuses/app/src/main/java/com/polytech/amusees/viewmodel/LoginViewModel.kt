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

            if(user.login.isNullOrEmpty())
                return@launch

            if(user.password.isNullOrEmpty())
                return@launch

            encodePassword()

            val id = testLogin()
            Log.i("ID",id.toString())
            if (id > 0) {
                _user.value = get(id)
                _navigateToListMuseesFragment.value = user
            }
        }
    }

    fun doneNavigating() {
        _navigateToListMuseesFragment.value = null
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

    private suspend fun testLogin(): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.testLogin(user.value?.login?:"",user.value?.password?:"")
        }
        return id
    }

    fun encodePassword() {
        user.value?.password = MessageDigest
            .getInstance("MD5")
            .digest((user.value?.password+"").toByteArray()).toString()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("LoginViewModel", "destroyed")
        viewModelJob.cancel()
    }
}