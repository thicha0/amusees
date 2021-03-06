package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.Request
import kotlinx.coroutines.*


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

    private val _params = MutableLiveData<Boolean>()
    val params: LiveData<Boolean>
        get() = _params

    init {
        Log.i("FormViewModel", "created")
        _request.value = Request()
    }

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = null
    }

    //log me in
    private val _navigateToListMuseesFragment = MutableLiveData<Request>()

    val navigateToListMuseesFragment: LiveData<Request>
        get() = _navigateToListMuseesFragment

    fun onRowsSelected(position: Int) {
        request.value?.rows = (1+position)*10
    }

    fun onSortSelected(position: Int) {
        var sortBy = "ville"
        when(position) {
            0->sortBy = "ville"
            1->sortBy = "nomdep"
            2->sortBy = "new_regions"
        }
        request.value?.sort = sortBy
    }

    fun onValidateSearch() {
        _navigateToListMuseesFragment.value = request.value
    }

    fun params(isChecked: Boolean) {
        _params.value = isChecked
    }

    fun doneNavigating() {
        _navigateToListMuseesFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("FormViewModel", "destroyed")
        viewModelJob.cancel()
    }
}