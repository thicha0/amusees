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
import androidx.navigation.fragment.findNavController
import com.polytech.amusees.database.MyDatabase
import com.polytech.amusees.databinding.FragmentLoginBinding
import com.polytech.amusees.viewmodel.LoginViewModel
import com.polytech.amusees.viewmodelfactory.LoginViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = MyDatabase.getInstance(application).userDao
        val viewModelFactory = LoginViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(LoginViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.login_title)
            btLogin.text = getString(R.string.login_button)
            btRegister.text = getString(R.string.register_title)
        }

        //Login
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


        //Register
        viewModel.navigateToRegister.observe(this, Observer { bool ->
            bool?.let {
                if (bool) {
                    this.findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToRegisterPersonnalFragment()
                    )
                    viewModel.doneNavigating()
                }
            }
        })

        //alert
        viewModel.alert.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                viewModel.doneAlerting()
            }
        })

        return binding.root
    }
}