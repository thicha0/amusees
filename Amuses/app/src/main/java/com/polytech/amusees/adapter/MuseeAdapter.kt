package com.polytech.amusees.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polytech.amusees.databinding.ItemMuseeViewBinding
import com.polytech.amusees.service.Record

class MuseeAdapter(val clickListener: MuseeListener) : ListAdapter<Record, MuseeAdapter.ViewHolder>(MuseeDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemMuseeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Record, clickListener: MuseeListener) {
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

class MuseeDiffCallback : DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem.recordid == newItem.recordid
    }

    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }
}


class MuseeListener(val clickListener: (museeId: String) -> Unit) {
    fun onClick(musee: Record) = clickListener(musee.recordid)
}