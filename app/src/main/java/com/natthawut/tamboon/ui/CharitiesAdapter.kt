package com.natthawut.tamboon.ui

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.natthawut.tamboon.R
import com.natthawut.tamboon.databinding.CharityItemBinding
import com.natthawut.tamboon.remote.Charity

class CharitiesAdapter : RecyclerView.Adapter<CharitiesAdapter.CharityViewHolder>() {

    var charities: List<Charity>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: CharityViewHolder?, position: Int) {
        val charity = charities?.get(position)
        if (charity != null) {
            holder?.bind(charity)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CharityViewHolder {
        val charityItemBinding = DataBindingUtil.inflate<CharityItemBinding>(
                LayoutInflater.from(parent?.context), R.layout.charity_item, parent, false)

        return CharityViewHolder(charityItemBinding)
    }

    override fun getItemCount(): Int {
        return charities?.size ?: 0
    }

    class CharityViewHolder(private val binding: CharityItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(charity: Charity) {
            binding.charity = charity
        }

    }

}
