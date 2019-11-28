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


class RegisterAccountViewModel(
    val database: UserDao,
    application: Application,
    private val myUser: User
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _verifPassword: String? = ""
    var verifPassword: String?
        get() = _verifPassword
        set(value) {
            _verifPassword = value
        }


    init {
        Log.i("RegisterAccountVM", "created")
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = myUser
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

    //end register
    private val _navigateToLoginFragment = MutableLiveData<User>()

    val navigateToLoginFragment: LiveData<User>
        get() = _navigateToLoginFragment


    fun onValidateAccount() {
        Log.i("200","Click account")
        uiScope.launch {
            val user = user.value ?: return@launch

            if(user.email.isNullOrEmpty()){
                _alert.value = "Veuillez saisir votre email"
                return@launch
            }

            if(!isFormatEmailOK()) {
                _alert.value = "Veuillez saisir un email correct (exemple@example.org)"
                return@launch
            }

            if(user.login.isNullOrEmpty()){
                _alert.value = "Veuillez saisir votre identifiant"
                return@launch
            }

            if(!isLoginAvailable()) {
                _alert.value = "Cet identifiant est indisponible, veuillez en saisir un nouveau"
                return@launch
            }

            if(user.password.isNullOrEmpty()) {
                _alert.value = "Veuillez saisir votre mot de passe"
                return@launch
            }

            if(user.password != verifPassword) {
                _alert.value = "Les mots de passes ne correspondent pas !"
                return@launch
            }

            if(!isPasswordSafe()) {
                _alert.value = "Votre mot de passe n'est pas assez sécurisé !\nCliquez sur l'icône d'information pour voir les conditions"
                return@launch
            }

            //TODO change 'cause it shows the SHA1 in the input of the fragment
            user.password = encode("SHA1",user.password+"")

            user.id = insert(user)

            _navigateToLoginFragment.value = user
        }
    }

    fun isFormatEmailOK(): Boolean {
        val email = user.value?.email+""
        val regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$" // example@example.org
        return email.matches(regex.toRegex())
    }

    //TODO get out of here (factoriser avec LoginViewModel)
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

    private suspend fun isLoginAvailable(): Boolean {
        val id = withContext(Dispatchers.IO) {
            database.existsLogin(user.value?.login+"")
        }

        return id == 0L
    }

    private fun isPasswordSafe(): Boolean {
        val pwd = user.value?.password+""

        //Length > 8
        if(pwd.length < 8) {
            Log.i("PWD","need length of minimum 8")
            return false
        }

        //Contains lowercase
        if (!pwd.matches(".*[a-z].*".toRegex())) {
            Log.i("PWD","need lowercase")
            return false
        }

        //Contains uppercase
        if (!pwd.matches(".*[A-Z].*".toRegex())) {
            Log.i("PWD","need uppercase")
            return false
        }

        //Contains number
        if (!pwd.matches(".*\\d.*".toRegex())) {
            Log.i("PWD","need number")
            return false
        }

        //Contains special character
        if (!pwd.matches(".*[~*$.|?!,;(){}/@%^#].*".toRegex())) {
            Log.i("PWD","need special character")
            return false
        }

        //Else OK
        return true
    }

    //gobackto personnal
    private val _navigateToRegisterLocationFragment = MutableLiveData<User>()

    val navigateToRegisterLocationFragment: LiveData<User>
        get() = _navigateToRegisterLocationFragment

    fun onGoBack() {
        uiScope.launch {
            val user = user.value ?: return@launch

            _navigateToRegisterLocationFragment.value = user
        }
    }

    fun doneNavigating() {
        _navigateToLoginFragment.value = null
        _navigateToRegisterLocationFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("RegisterAccountVM", "destroyed")
        viewModelJob.cancel()
    }
}