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
import com.polytech.amusees.databinding.FragmentRegisterPersonnalBinding
import java.util.*

class RegisterPersonnalFragment : Fragment() {
    private lateinit var binding: FragmentRegisterPersonnalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_personnal, container, false)
        binding.lifecycleOwner = this

        binding.apply {
            tvTitle.text = getString(R.string.register_title)
            btNext.text = getString(R.string.next_button)
        }

        binding.tiBirthday.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(activity!!,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    binding.tiBirthday.text =  SpannableStringBuilder("$dayOfMonth/$monthOfYear/$year")
                    //viewModel.user.value?.birthdayDate = Date(year,monthOfYear,dayOfMonth).time

                }, year, month, day)

            dpd.show()
        }

        binding.btNext.setOnClickListener {
            //TODO: vérification des champs
            nextStepRegister(it)
        }

        return binding.root
    }

    private fun nextStepRegister(view: View) {
        Toast.makeText(activity, "Données personnelles remplies",Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_registerPersonnalFragment_to_registerLocationFragment)
    }
}