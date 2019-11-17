package com.polytech.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polytech.myapplication.R

class MuseeItemAdapter(val response: Response, val context: Context) : RecyclerView.Adapter<MuseeItemAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.musee_item_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
}