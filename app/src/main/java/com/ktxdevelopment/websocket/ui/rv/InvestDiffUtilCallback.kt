package com.ktxdevelopment.websocket.ui.rv

import androidx.recyclerview.widget.DiffUtil
import com.ktxdevelopment.websocket.model.remote.InvestItem

class InvestDiffUtilCallback(private val oldList: List<InvestItem>, private val newList: List<InvestItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].company == newList[newItemPosition].company
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when (oldList[oldItemPosition].date) {
            newList[newItemPosition].date -> true
            else -> false
        }
    }
}