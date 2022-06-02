package com.example.newsapp.shared.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class RecyclerViewAdapterBase<M : BaseCellModel, V : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<V>() {

    protected var items: MutableList<M> = ArrayList()

    fun autoNotify(
        newList: List<M>
    ) {
        val oldList = getCurrentItems()
        updateItems(newList)
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].cellId == newList[newItemPosition].cellId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(item: M?) {
        item ?: return
        items.add(item)
    }

    fun add(position: Int, item: M?) {
        item ?: return
        items.add(position, item)
    }

    fun addAll(list: List<M>?) {
        list ?: return
        items.addAll(list)
    }

    fun set(position: Int, item: M?) {
        item ?: return
        items[position] = item
    }

    private fun updateItems(list: List<M>?) {
        list ?: return
        items = ArrayList(list)
    }

    fun removeItem(position: Int) {
        if (position in 0 until items.size) {
            items.removeAt(position)
        }
    }

    fun removeItem(item: M?) {
        item ?: return
        val itemsPosition = items.indexOf(item)
        if (itemsPosition in 0 until items.size) {
            items.remove(item)
        }
    }

    fun clear() {
        items.clear()
    }

    private fun getCurrentItems(): ArrayList<M> {
        return ArrayList(items)
    }

    fun getItem(index: Int): M {
        return items[index]
    }

    fun size(): Int {
        return items.size
    }
}