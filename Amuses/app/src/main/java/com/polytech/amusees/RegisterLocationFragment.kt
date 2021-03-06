package com.polytech.amusees

import android.os.Bundle
import android.util.Log
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
import com.polytech.amusees.databinding.FragmentRegisterLocationBinding
import com.polytech.amusees.viewmodel.Countries
import com.polytech.amusees.viewmodel.RegisterLocationViewModel
import com.polytech.amusees.viewmodelfactory.RegisterLocationViewModelFactory


class RegisterLocationFragment : Fragment() {
    private lateinit var binding: FragmentRegisterLocationBinding
    private lateinit var viewModel: RegisterLocationViewModel
    private lateinit var viewModelFactory: RegisterLocationViewModelFactory

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
        val viewModelFactory = RegisterLocationViewModelFactory(dataSource, application,args.user)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(RegisterLocationViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.register_title)
            btNext.text = getString(R.string.next_button)
            btPrevious.text = getString(R.string.previous_button)
            spCountry.adapter = ArrayAdapter<String>(application,android.R.layout.simple_list_item_1, Countries.ZIM.countryNames())
            spCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    viewModel?.onCountrySelected(position)
                }

            }
        }

        viewModel.alert.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                viewModel.doneAlerting()
            }
        })

        //continue
        viewModel.navigateToRegisterAccountFragment.observe(this, Observer { user ->
            user?.let {
                Log.i("User",viewModel.user.value.toString())

                this.findNavController().navigate(RegisterLocationFragmentDirections.actionRegisterLocationFragmentToRegisterAccountFragment(user))
                viewModel.doneNavigating()
            }
        })

        //goback
        viewModel.navigateToPersonnalAccountFragment.observe(this, Observer { user ->
            user?.let {
                Log.i("User",viewModel.user.value.toString())

                this.findNavController().navigate(RegisterLocationFragmentDirections.actionRegisterLocationFragmentToRegisterPersonnalFragment(user))
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}