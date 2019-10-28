package com.polytech.amusees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.polytech.amusees.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this

        binding.apply {
            twTitle.text = getString(R.string.home_title)
            btLogin.text = getString(R.string.home_login)
            btRegister.text = getString(R.string.home_register)
        }

        binding.btLogin.setOnClickListener {
            login(it)
        }

        binding.btRegister.setOnClickListener {
            register(it)
        }

        return binding.root
    }

    private fun login(view: View) {
        Toast.makeText(activity, "Connexion !", Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    private fun register(view: View) {
        Toast.makeText(activity, "Créer un compte !", Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
    }
}