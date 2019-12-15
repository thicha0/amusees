package com.polytech.amusees.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.amusees.model.Musee
import com.polytech.amusees.model.Request
import com.polytech.amusees.service.MyApi
import com.polytech.amusees.service.Record
import kotlinx.coroutines.*

class ListMuseesViewModel(request: Request) : ViewModel() {

    private lateinit var request: Request

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _musees = MutableLiveData<List<Musee>>()
    val musees: LiveData<List<Musee>>
        get() = _musees

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _nbPages = MutableLiveData<Int>()
    val nbPages: LiveData<Int>
        get() = _nbPages

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        this.request = request
        getMuseesList()
    }

    private fun getMuseesList() {
        _isLoading.value = true
        coroutineScope.launch {
            var getMuseesDeferred = MyApi.retrofitService.getMusees(""+request.page,
                ""+request.rows,
                ""+request.sort,
                ""+request.query?.toLowerCase())
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
                _currentPage.value = request.page + 1
                _nbPages.value = result.nhits / request.rows
            } catch (e: Exception) {
                _response.value = "Echec: ${e.message}"
            }
            _isLoading.value = false
            Log.i("getMusee","done "+_response.value)
            Log.i("nb pages", ""+nbPages.value)
        }
    }

    fun goToPrecedentPage() {
        this.request.page = this.request.page - request.rows
        getMuseesList()
    }

    fun goToNextPage() {
        this.request.page = this.request.page + request.rows
        getMuseesList()
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