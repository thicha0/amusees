package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.Musee
import com.polytech.amusees.model.User
import com.polytech.amusees.service.Record
import kotlinx.coroutines.*

class DetailsViewModel(
    application: Application,
    private val myMusee: Musee
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _musee = MutableLiveData<Musee>()
    val musee: LiveData<Musee>
        get() = _musee

    init {
        Log.i("RegisterLocationVM", "created")
        initializeMusee()
    }

    private fun initializeMusee() {
        uiScope.launch {
            _musee.value = myMusee
        }
    }

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("DetailsViewModel", "destroyed")
        viewModelJob.cancel()
    }
}