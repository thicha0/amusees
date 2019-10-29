package com.polytech.amusees

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.polytech.amusees.databinding.FragmentRegisterAccountBinding
import java.util.*

class RegisterAccountFragment : Fragment() {
    private lateinit var binding: FragmentRegisterAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_account, container, false)
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
        Toast.makeText(activity, "Compte créé, vous pouvez vous connecter !", Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_registerAccountFragment_to_loginFragment)
    }

    private fun goBackPreviousStepRegister(view: View) {
        Toast.makeText(activity, "Retour aux données de location", Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_registerAccountFragment_to_registerLocationFragment)
    }
}