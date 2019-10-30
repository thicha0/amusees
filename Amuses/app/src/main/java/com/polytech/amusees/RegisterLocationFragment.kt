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
import com.polytech.amusees.databinding.FragmentRegisterLocationBinding
import com.polytech.amusees.viewmodel.RegisterViewModel
import com.polytech.amusees.viewmodelfactory.RegisterViewModelFactory

class RegisterLocationFragment : Fragment() {
    private lateinit var binding: FragmentRegisterLocationBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var viewModelFactory: RegisterViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_location, container, false)
        binding.lifecycleOwner = this

        val args = RegisterLocationFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val dataSource = MyDatabase.getInstance(application).userDao
        val viewModelFactory = RegisterViewModelFactory(dataSource, application,args.user.id)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(RegisterViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.register_title)
            btNext.text = getString(R.string.next_button)
        }

        viewModel.navigateToRegisterAccountFragment.observe(this, Observer { user ->
            user?.let {
                val message = viewModel.user.value?.adress + " " +
                        viewModel.user.value?.city + " " +
                        viewModel.user.value?.country
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

                this.findNavController().navigate(RegisterLocationFragmentDirections.actionRegisterLocationFragmentToRegisterAccountFragment(user))
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}