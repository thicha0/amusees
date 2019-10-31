package com.polytech.amusees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.polytech.amusees.database.MyDatabase
import com.polytech.amusees.databinding.FragmentLoginBinding
import com.polytech.amusees.viewmodel.RegisterViewModel
import com.polytech.amusees.viewmodelfactory.RegisterViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var viewModelFactory: RegisterViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = MyDatabase.getInstance(application).userDao
        val viewModelFactory = RegisterViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(RegisterViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.login_title)
            btLogin.text = getString(R.string.login_button)
        }

        viewModel.navigateToListMuseesFragment.observe(this, Observer { user ->
            user?.let {
                val message = "Bienvenue " +
                        viewModel.user.value?.login + " !"
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

                this.findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToListMuseesFragment(user)
                )
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}