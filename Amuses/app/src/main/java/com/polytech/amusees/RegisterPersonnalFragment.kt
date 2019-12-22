package com.polytech.amusees

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.databinding.InverseMethod
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.polytech.amusees.database.MyDatabase
import com.polytech.amusees.databinding.FragmentRegisterPersonnalBinding
import java.text.SimpleDateFormat
import java.util.*
import com.polytech.amusees.viewmodel.RegisterPersonnalViewModel
import com.polytech.amusees.viewmodelfactory.RegisterPersonnalViewModelFactory

object LongConverter {
    @JvmStatic
    @InverseMethod("stringToDate")
    fun dateToString(
        value: Long
    ): String {
        val date = Date(value)
        val f = SimpleDateFormat("dd/MM/yy")
        val dateText = f.format(date)
        return dateText
    }

    @JvmStatic
    fun stringToDate(        value: String
    ): Long {
        val f = SimpleDateFormat("dd/MM/yy")
        val d = f.parse(value)
        return d.time
    }
}

class RegisterPersonnalFragment : Fragment() {
    private lateinit var binding: FragmentRegisterPersonnalBinding
    private lateinit var viewModel: RegisterPersonnalViewModel
    private lateinit var viewModelFactory: RegisterPersonnalViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_personnal, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = MyDatabase.getInstance(application).userDao
        val args = RegisterLocationFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory = RegisterPersonnalViewModelFactory(dataSource, application, args.user)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(RegisterPersonnalViewModel::class.java)

        binding.viewModel = viewModel

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
                    viewModel.user.value?.birthdayDate = Date(year,monthOfYear,dayOfMonth).time

                }, year, month, day)
            dpd.show()
        }

        viewModel.alert.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                viewModel.doneAlerting()
            }
        })

        //continue
        viewModel.navigateToRegisterLocationFragment.observe(this, Observer { user ->
            user?.let {
                this.findNavController().navigate(RegisterPersonnalFragmentDirections.actionRegisterPersonnalFragmentToRegisterLocationFragment(user))
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}