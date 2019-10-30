package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.User
import kotlinx.coroutines.*

class RegisterViewModel(
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
        Log.i("IdentityViewModel", "created")
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

    //goto Location
    private val _navigateToRegisterLocationFragment = MutableLiveData<User>()

    val navigateToRegisterLocationFragment: LiveData<User>
        get() = _navigateToRegisterLocationFragment

    fun onValidatePersonal() {
        Log.i("200","Click personal")
        uiScope.launch {
            val user = user.value ?: return@launch

            if(user.firstname.isNullOrEmpty())
                return@launch

            if(user.lastname.isNullOrEmpty())
                return@launch

            if(user.gender.isNullOrEmpty())
                return@launch

            //TODO meilleur test ?
            if(user.birthdayDate == 0L)
                return@launch

            update(user)

            _navigateToRegisterLocationFragment.value = user
        }
    }

    //goto account
    private val _navigateToRegisterAccountFragment = MutableLiveData<User>()

    val navigateToRegisterAccountFragment: LiveData<User>
        get() = _navigateToRegisterAccountFragment


    fun onValidateLocation() {
        Log.i("200","Click location")
        uiScope.launch {
            val user = user.value ?: return@launch

            //verif les precedents ?

            if(user.adress.isNullOrEmpty())
                return@launch

            if(user.city.isNullOrEmpty())
                return@launch

            if(user.country.isNullOrEmpty())
                return@launch

            update(user)

            _navigateToRegisterAccountFragment.value = user
        }
    }

    //end register
    private val _navigateToLoginFragment = MutableLiveData<User>()

    val navigateToLoginFragment: LiveData<User>
        get() = _navigateToLoginFragment


    fun onValidateAccount() {
        Log.i("200","Click account")
        uiScope.launch {
            val user = user.value ?: return@launch

            //verif les precedents ?

            if(user.email.isNullOrEmpty())
                return@launch

            if(user.login.isNullOrEmpty())
                return@launch

            if(user.password.isNullOrEmpty())
                return@launch

            update(user)

            _navigateToLoginFragment.value = user
        }
    }

    fun doneNavigating() {
        _navigateToRegisterLocationFragment.value = null
        _navigateToRegisterAccountFragment.value = null
        _navigateToLoginFragment.value = null
    }

    private suspend fun update(user: User) {
        withContext(Dispatchers.IO) {
            database.update(user)
        }
    }

    private suspend fun get(id: Long) {
        withContext(Dispatchers.IO) {
            database.get(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("RegisterViewModel", "destroyed")
        viewModelJob.cancel()
    }
}