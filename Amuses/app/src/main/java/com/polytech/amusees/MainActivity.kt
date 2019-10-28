package com.polytech.amusees

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.polytech.amusees.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            twTitle.text = getString(R.string.title)
            btConnexion.text = getString(R.string.connection)
            btCreerCompte.text = getString(R.string.create_account)
        }

        binding.btConnexion.setOnClickListener {
            connect(it)
        }

        binding.btCreerCompte.setOnClickListener {
            createAccount(it)
        }
    }

    private fun connect(view: View) {
        Toast.makeText(this, "Connexion !",Toast.LENGTH_SHORT).show()
    }

    private fun createAccount(view: View) {
        Toast.makeText(this, "Créer un compte !",Toast.LENGTH_SHORT).show()
    }
}
