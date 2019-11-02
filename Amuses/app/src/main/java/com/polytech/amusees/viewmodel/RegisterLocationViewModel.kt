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



//TODO Bind spinner with enum
enum class Countries {
    ANDORRE,
    AFGHANISTAN,
    FRANCE
}

class RegisterLocationViewModel(
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
        Log.i("RegisterLocationVM", "created")
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

            update(user)

            _navigateToRegisterAccountFragment.value = user
        }
    }

    fun doneNavigating() {
        _navigateToRegisterAccountFragment.value = null
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
        Log.i("RegisterLocationVM", "destroyed")
        viewModelJob.cancel()
    }
}