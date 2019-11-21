package com.polytech.amusees.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.amusees.model.Musee
import com.polytech.amusees.service.MyApi
import com.polytech.amusees.service.Record
import kotlinx.coroutines.*

//class ListMuseesViewModel(
//    val database: UserDao,
//    application: Application,
//    private val userID: Long = 0L // userID
//) : AndroidViewModel(application)
//{
//
//    private var viewModelJob = Job()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//
//    private val _user = MutableLiveData<User>()
//    val user: LiveData<User>
//        get() = _user
//
//    private val _musees = MutableLiveData<List<Musee>>()
//    val musees: LiveData<List<Musee>>
//        get() = _musees
//
//    init {
//        Log.i("ListMuseesViewModel", "created")
//        //initializeUser()
//        getAllMusees()
//    }
//
//    private fun initializeUser() {
//        uiScope.launch {
//            _user.value = getUserFromDatabase()
//        }
//    }
//
//    private fun getAllMusees() {
//        uiScope.launch {
//            var getMusees = MyApi.retrofitService.getMusees()
//            try {
//                var listResult = getMusees.await()
//                Log.i("getAllMusees","success "+listResult.size+" musees retrieved")
//                _musees.value = listResult
//
//            } catch (e: Exception) {
//                Log.i("getAllMusees","fail : "+e.message)
//            }
//        }
//    }
//
//    //gobackto login if user not allowed
//    private val _navigateToLoginFragment = MutableLiveData<Int>()
//
//    val navigateToLoginFragment: LiveData<Int>
//        get() = _navigateToLoginFragment
//
//    private suspend fun getUserFromDatabase(): User? {
//        return withContext(Dispatchers.IO) {
//
//            var user = database.get(userID) // userID
//            if (user == null) {
//                _navigateToLoginFragment.value = 1
//            }
//            user
//        }
//    }
//
//    fun doneNavigating() {
//        _navigateToLoginFragment.value = null
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.i("ListMuseesViewModel", "destroyed")
//        viewModelJob.cancel()
//    }
//}

// TODO Trier dessus

class ListMuseesViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private val _musees = MutableLiveData<List<Musee>>()
    val musees: LiveData<List<Musee>>
        get() = _musees

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getMuseesList()
    }

    private fun getMuseesList() {
        coroutineScope.launch {
            var getMuseesDeferred = MyApi.retrofitService.getMusees()
            try {
                Log.i("getMusee","started")
                var result = getMuseesDeferred.await()
                var listResult = result.records
                _response.value = "Succès: ${listResult.size} musées récupérés"

                var listMusee : MutableList<Musee> = mutableListOf()
                var index = 0
                for (record in listResult) {
                    listMusee.add(index, recordToMusee(record))
                    Log.i("Musee","+1")
                    index++
                }
                _musees.value = listMusee
            } catch (e: Exception) {
                _response.value = "Echec: ${e.message}"
            }
            Log.i("getMusee","done "+_response.value)
        }
    }

    private fun recordToMusee(record: Record): Musee {
        return Musee(record.fields?.nom_du_musee,
            record.fields?.new_regions,
            record.fields?.nomdep,
            record.fields?.ville,
            record.fields?.adr,
            record.fields?.coordonnees_finales?.get(0)?:48.8566,
            record.fields?.coordonnees_finales?.get(1)?:2.3522,
            record.fields?.sitweb,
            record.fields?.telephone1,
            record.fields?.periode_ouverture,
            record.fields?.ref_musee)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}