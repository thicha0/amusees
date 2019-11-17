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
            btEnter.text = getString(R.string.enter)
        }

        binding.btEnter.setOnClickListener {
            enter(it)
        }

        return binding.root
    }

    private fun enter(view: View) {
        Toast.makeText(activity, "Connexion !", Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }
}