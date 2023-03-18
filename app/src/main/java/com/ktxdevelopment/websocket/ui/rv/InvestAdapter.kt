package com.ktxdevelopment.websocket.ui.rv

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ktxdevelopment.websocket.databinding.ItemInvestListBinding
import com.ktxdevelopment.websocket.model.remote.InvestItem

class InvestAdapter(private val onInvestClickListener: OnInvestClickListener) : ListAdapter<InvestItem, InvestAdapter.InvestViewHolder>(Diff) {

    class InvestViewHolder(binding: ItemInvestListBinding) : ViewHolder(binding.root)

    object Diff : DiffUtil.ItemCallback<InvestItem>() {
        override fun areItemsTheSame(oldItem: InvestItem, newItem: InvestItem): Boolean {
            return newItem.company == oldItem.company
        }
        override fun areContentsTheSame(old: InvestItem, new: InvestItem): Boolean{
            return (new.date == old.date && new.val1 == old.val1 && new.val2 == old.val2 && new.val3 == old.val3 && new.val4 == old.val4)
        }
    }




    interface OnInvestClickListener {
        fun onInvestClick(item: InvestItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestViewHolder {
        return InvestViewHolder(ItemInvestListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: InvestViewHolder, position: Int) {

    }
}