package com.polytech.amusees

import android.os.Bundle
import android.util.Log
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
import com.polytech.amusees.viewmodel.RegisterAccountViewModel
import com.polytech.amusees.viewmodelfactory.RegisterAccountViewModelFactory

class RegisterAccountFragment : Fragment() {
    private lateinit var binding: FragmentRegisterAccountBinding
    private lateinit var viewModel: RegisterAccountViewModel
    private lateinit var viewModelFactory: RegisterAccountViewModelFactory

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
        val viewModelFactory = RegisterAccountViewModelFactory(dataSource, application, args.user)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(RegisterAccountViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.register_title)
            btNext.text = getString(R.string.next_button)
            btPrevious.text = getString(R.string.previous_button)
        }

        binding.ibInformation.setOnClickListener {
            Toast.makeText(activity, getString(R.string.password_information), Toast.LENGTH_LONG).show()
        }

        viewModel.alert.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                viewModel.doneAlerting()
            }
        })

        //end
        viewModel.navigateToLoginFragment.observe(this, Observer { user ->
            user?.let {
                Log.i("User",viewModel.user.value.toString())

                this.findNavController().navigate(
                    RegisterAccountFragmentDirections.actionRegisterAccountFragmentToLoginFragment()
                )
                viewModel.doneNavigating()
            }
        })

        //goback
        viewModel.navigateToRegisterLocationFragment.observe(this, Observer { user ->
            user?.let {
                Log.i("User",viewModel.user.value.toString())

                this.findNavController().navigate(RegisterAccountFragmentDirections.actionRegisterAccountFragmentToRegisterLocationFragment(user))
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}