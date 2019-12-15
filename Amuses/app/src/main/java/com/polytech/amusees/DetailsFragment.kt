package com.polytech.amusees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.polytech.amusees.databinding.FragmentDetailsBinding
import com.polytech.amusees.viewmodel.DetailsViewModel
import com.polytech.amusees.viewmodelfactory.DetailsViewModelFactory

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private lateinit var viewModelFactory: DetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.lifecycleOwner = this

        val args = DetailsFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = DetailsViewModelFactory(application,args.musee)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(DetailsViewModel::class.java)

        binding.viewModel = viewModel

        binding.apply {
            tvRegion1.text = getString(R.string.region)
            tvDep1.text = getString(R.string.departement)
            tvVille1.text = getString(R.string.ville)
            tvAdresse1.text = getString(R.string.adresse)
            tvSiteweb1.text = getString(R.string.siteweb)
            tvTelephone1.text = getString(R.string.telephone)
            tvOuverture1.text = getString(R.string.ouverture)

        }

        return binding.root
    }
}