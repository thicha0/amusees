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
import com.polytech.amusees.adapter.MuseeAdapter
import com.polytech.amusees.adapter.MuseeListener
import com.polytech.amusees.database.MyDatabase
import com.polytech.amusees.databinding.FragmentListMuseesBinding
import com.polytech.amusees.viewmodel.ListMuseesViewModel
import com.polytech.amusees.viewmodelfactory.ListMuseesViewModelFactory

class ListMuseesFragment : Fragment() {
    private lateinit var binding: FragmentListMuseesBinding
    private lateinit var viewModel: ListMuseesViewModel
    private lateinit var viewModelFactory: ListMuseesViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_musees, container, false)
        binding.lifecycleOwner = this

        val args = ListMuseesFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val dataSource = MyDatabase.getInstance(application).userDao
        val viewModelFactory = ListMuseesViewModelFactory(application,args.request)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ListMuseesViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvTitle.text = getString(R.string.result_title)
            btNextPage.text = getString(R.string.nextPage)
            btPrecedentPage.text = getString(R.string.precedentPage)
        }

        val adapter = MuseeAdapter(MuseeListener { musee ->
            this.findNavController().navigate(
                ListMuseesFragmentDirections.actionListMuseesFragmentToDetailsFragment(musee)
            )
        })
        binding.list.adapter = adapter

        viewModel.musees.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it.let {
                if(it) {
                    binding.load.visibility = View.VISIBLE
                    binding.list.visibility = View.GONE
                    binding.btNextPage.visibility = View.GONE
                    binding.btPrecedentPage.visibility = View.GONE
                }
                else {
                    binding.load.visibility = View.GONE
                    binding.list.visibility = View.VISIBLE

                    if (viewModel.currentPage.value?.compareTo(1) != -1) {
                        binding.btPrecedentPage.visibility = View.VISIBLE
                    }

                    if (viewModel.currentPage.value != viewModel.nbPages.value) {
                        binding.btNextPage.visibility = View.VISIBLE
                    }

                }
            }
        })

        return binding.root
    }
}