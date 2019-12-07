package com.polytech.amusees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.polytech.amusees.database.MyDatabase
import com.polytech.amusees.databinding.FragmentFormBinding
import com.polytech.amusees.model.User
import com.polytech.amusees.viewmodel.Countries
import com.polytech.amusees.viewmodel.FormViewModel
import com.polytech.amusees.viewmodelfactory.FormViewModelFactory

class FormFragment : Fragment() {
    private lateinit var binding: FragmentFormBinding
    private lateinit var viewModel: FormViewModel
    private lateinit var viewModelFactory: FormViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = MyDatabase.getInstance(application).userDao
        val viewModelFactory = FormViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(FormViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.form_title)
        }

        //Form
        viewModel.navigateToListMuseesFragment.observe(this, Observer { user ->
            user?.let {

            }
        })


        //Register
        viewModel.navigateToRegister.observe(this, Observer { bool ->
            bool?.let {

            }
        })

        //Alerts
        viewModel.alert.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                viewModel.doneAlerting()
            }
        })

        return binding.root
    }
}