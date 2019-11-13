package com.polytech.amusees.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polytech.amusees.databinding.ItemDebugViewBinding
import com.polytech.amusees.databinding.ItemMuseeViewBinding
import com.polytech.amusees.model.Musee
import com.polytech.amusees.model.User

class MuseeAdapter : ListAdapter<Musee, MuseeAdapter.ViewHolder>(MuseeDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemMuseeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Musee) {
            binding.musee = item
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
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Musee, newItem: Musee): Boolean {
        return oldItem == newItem
    }
}


class MuseeListener(val clickListener: (museeId: Long) -> Unit) {
    fun onClick(musee: Musee) = clickListener(musee.id)
}