package com.polytech.amusees.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polytech.amusees.databinding.ItemMuseeViewBinding
import com.polytech.amusees.model.Musee

class MuseeAdapter(val clickListener: MuseeListener) : ListAdapter<Musee, MuseeAdapter.ViewHolder>(MuseeDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemMuseeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Musee, clickListener: MuseeListener) {
            binding.musee = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMuseeViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class MuseeDiffCallback : DiffUtil.ItemCallback<Musee>() {
    override fun areItemsTheSame(oldItem: Musee, newItem: Musee): Boolean {
        return oldItem.refmusee == newItem.refmusee
    }

    override fun areContentsTheSame(oldItem: Musee, newItem: Musee): Boolean {
        return oldItem == newItem
    }
}


class MuseeListener(val clickListener: (musee: Musee) -> Unit) {
    fun onClick(musee: Musee) = clickListener(musee)
}