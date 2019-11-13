package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.Musee
import com.polytech.amusees.model.User
import com.polytech.amusees.service.MyApi
import kotlinx.coroutines.*
import retrofit2.await

class ListMuseesViewModel(
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

    private val _musees = MutableLiveData<List<Musee>>()
    val musees: LiveData<List<Musee>>
        get() = _musees

    init {
        Log.i("ListMuseesViewModel", "created")
        //initializeUser()
        getAllMusees()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = getUserFromDatabase()
        }
    }

    private fun getAllMusees() {
        uiScope.launch {
            var getMusees = MyApi.retrofitService.getMusees()
            try {
                var listResult = getMusees.await()
                Log.i("getAllMusees","success "+listResult.size+" musees retrieved")
                _musees.value = listResult

            } catch (e: Exception) {
                Log.i("getAllMusees","fail : "+e.message)
            }
        }
    }

    //gobackto login if user not allowed
    private val _navigateToLoginFragment = MutableLiveData<Int>()

    val navigateToLoginFragment: LiveData<Int>
        get() = _navigateToLoginFragment

    private suspend fun getUserFromDatabase(): User? {
        return withContext(Dispatchers.IO) {

            var user = database.get(userID) // userID
            if (user == null) {
                _navigateToLoginFragment.value = 1
            }
            user
        }
    }

    fun doneNavigating() {
        _navigateToLoginFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ListMuseesViewModel", "destroyed")
        viewModelJob.cancel()
    }
}