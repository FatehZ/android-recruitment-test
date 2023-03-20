package com.ktxdevelopment.websocket.ui.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ktxdevelopment.websocket.R
import com.ktxdevelopment.websocket.databinding.ItemInvestListBinding
import com.ktxdevelopment.websocket.model.remote.InvestItem
import com.ktxdevelopment.websocket.util.Constants

class InvestAdapter : RecyclerView.Adapter<InvestAdapter.InvestViewHolder>() {

    private val currentList: ArrayList<InvestItem> = arrayListOf()

    class InvestViewHolder(val binding: ItemInvestListBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestViewHolder {
        return InvestViewHolder(ItemInvestListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: InvestViewHolder, position: Int) {
        val current = currentList[position]
        holder.binding.tvCompany.text = current.company
        holder.binding.tvRank.text = current.rank.toString()

        if (current.trend.lowercase() == Constants.Trend.UP) holder.binding.ivArrow.setImageResource(R.drawable.arrow_up)
        else holder.binding.ivArrow.setImageResource(R.drawable.arrow_down)

        holder.binding.tvVal1.text = current.val1
        holder.binding.tvVal2.text = current.val2
        holder.binding.tvVal3.text = current.val3
        holder.binding.tvVal4.text = current.val4
    }

    override fun getItemCount() = currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<InvestItem>) {
        currentList.clear()
        currentList.addAll(list)
        notifyDataSetChanged()

        val diffResult = DiffUtil.calculateDiff(InvestDiffUtilCallback(currentList, list))
        currentList.clear()
        currentList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}