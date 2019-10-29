package com.polytech.amusees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.polytech.amusees.databinding.FragmentRegisterLocationBinding

class RegisterLocationFragment : Fragment() {
    private lateinit var binding: FragmentRegisterLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_location, container, false)
        binding.lifecycleOwner = this

        binding.apply {
            tvTitle.text = getString(R.string.register_title)
            btNext.text = getString(R.string.next_button)
            btPrevious.text = getString(R.string.previous_button)
        }

        binding.btNext.setOnClickListener {
            //TODO: vérification des champs
            nextStepRegister(it)
        }

        binding.btPrevious.setOnClickListener {
            goBackPreviousStepRegister(it)
        }

        return binding.root
    }

    private fun nextStepRegister(view: View) {
        Toast.makeText(activity, "Données de locations remplies", Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_registerLocationFragment_to_registerAccountFragment)
    }

    private fun goBackPreviousStepRegister(view: View) {
        Toast.makeText(activity, "Retour aux données personnelles", Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_registerLocationFragment_to_registerPersonnalFragment)
    }
}