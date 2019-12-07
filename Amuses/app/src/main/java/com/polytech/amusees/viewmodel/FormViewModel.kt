package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.Column
import com.polytech.amusees.model.Request
import com.polytech.amusees.model.User
import kotlinx.coroutines.*
import java.security.MessageDigest


class FormViewModel(
    val database: UserDao,
    application: Application
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _request = MutableLiveData<Request>()
    val request: LiveData<Request>
        get() = _request

    init {
        Log.i("FormViewModel", "created")
        //
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

    fun onRefineSelected(position: Int) {
        request.value?.refine = Column.values()[position]
    }

    fun onRowsSelected(position: Int) {
        request.value?.rows = position*10
    }

    fun onSortSelected(position: Int) {
        request.value?.sort = Column.values()[position]
    }

    //register
    private val _navigateToRegister = MutableLiveData<Boolean>()

    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister


    fun onValidateSearch() {
        //
    }

    fun onAdvancedParams() {
        //
    }

    fun doneNavigating() {
        _navigateToListMuseesFragment.value = null
        _navigateToRegister.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("FormViewModel", "destroyed")
        viewModelJob.cancel()
    }
}