package com.example.aaaaaaaaaaa

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aaaaaaaaaaa.databinding.DateItemBinding

class DateItemAdapter(private val context: Context, private val dateItemList:List<DateItem>)
    : RecyclerView.Adapter<DateItemAdapter.DateItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateItemViewHolder {
        val binding = DateItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return DateItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateItemViewHolder, position: Int) {
        val dateItem = dateItemList[position]
        holder.bind(dateItem)
    }

    override fun getItemCount(): Int {
        return dateItemList.size
    }

    class DateItemViewHolder(dateItemBinding: DateItemBinding)
        : RecyclerView.ViewHolder(dateItemBinding.root){

        private val binding = dateItemBinding

        fun bind(dateItem: DateItem){
            binding.date.text = dateItem.text
        }

    }

    data class DateItem(
        var text : String,
        var chosen: Boolean
    )
}