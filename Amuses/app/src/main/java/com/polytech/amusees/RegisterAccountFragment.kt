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
import com.polytech.amusees.databinding.FragmentRegisterAccountBinding
import com.polytech.amusees.viewmodel.RegisterViewModel
import com.polytech.amusees.viewmodelfactory.RegisterViewModelFactory

class RegisterAccountFragment : Fragment() {
    private lateinit var binding: FragmentRegisterAccountBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var viewModelFactory: RegisterViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register_account, container, false)
        binding.lifecycleOwner = this

        val args = RegisterAccountFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val dataSource = MyDatabase.getInstance(application).userDao
        val viewModelFactory = RegisterViewModelFactory(dataSource, application, args.user.id)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(RegisterViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.register_title)
            btNext.text = getString(R.string.next_button)
        }

        viewModel.navigateToLoginFragment.observe(this, Observer { user ->
            user?.let {
                val message = viewModel.user.value?.email + " " +
                        viewModel.user.value?.login + " " +
                        viewModel.user.value?.password
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

                this.findNavController().navigate(
                    RegisterAccountFragmentDirections.actionRegisterAccountFragmentToLoginFragment()
                )
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}