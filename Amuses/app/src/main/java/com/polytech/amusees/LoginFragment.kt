package com.polytech.amusees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.polytech.amusees.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this

        binding.apply {
            tvTitle.text = getString(R.string.login_title)
            btLogin.text = getString(R.string.login_button)
        }

        binding.btLogin.setOnClickListener {
            login(it)
        }

        return binding.root
    }

    private fun login(view: View) {
        Toast.makeText(activity, "Connexion en cours...", Toast.LENGTH_SHORT).show()
        //TODO: Vérif DB + Connexion

    }
}