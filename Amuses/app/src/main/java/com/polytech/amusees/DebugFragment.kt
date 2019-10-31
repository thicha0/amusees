package com.polytech.amusees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.polytech.amusees.adapter.DebugAdapter
import com.polytech.amusees.database.MyDatabase
import com.polytech.amusees.databinding.FragmentDebugBinding
import com.polytech.amusees.model.User
import com.polytech.amusees.viewmodel.DebugViewModel
import com.polytech.amusees.viewmodelfactory.DebugViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class DebugFragment :  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDebugBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_debug, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = MyDatabase.getInstance(application).userDao
        val viewModelFactory = DebugViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(DebugViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = DebugAdapter()
        binding.list.adapter = adapter

        viewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}

@BindingAdapter("userDate")
fun TextView.setUserDate(item: User) {
    val date = Date(item.birthdayDate)
    val f = SimpleDateFormat("dd/MM/yy")
    val dateText = f.format(date)
    text = dateText
}
@BindingAdapter("userImage")
fun ImageView.setUserImage(item: User) {
    setImageResource(when (item.gender) {
        "Homme" -> R.mipmap.ic_man
        else -> R.mipmap.ic_woman
    })
}
