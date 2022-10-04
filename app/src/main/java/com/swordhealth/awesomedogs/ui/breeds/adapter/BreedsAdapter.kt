package com.swordhealth.awesomedogs.ui.breeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swordhealth.awesomedogs.databinding.ItemGridBreedBinding
import com.swordhealth.awesomedogs.databinding.ItemListBreedBinding
import com.swordhealth.awesomedogs.ui.BreedsEventHandler
import com.swordhealth.awesomedogs.ui.breeds.adapter.viewholders.BreedGridItemViewHolder
import com.swordhealth.awesomedogs.ui.breeds.adapter.viewholders.BreedListItemViewHolder
import com.swordhealth.awesomedogs.utils.BreedDiffCallback
import objects.BreedPresentation

class BreedsAdapter(
    private val breedsEventHandler: BreedsEventHandler
) : ListAdapter<BreedPresentation, RecyclerView.ViewHolder>(
    BreedDiffCallback()
) {

    private var isListView = false

    fun changeViewStyle() {
        isListView = !isListView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val binding = ItemListBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BreedListItemViewHolder(binding, breedsEventHandler)
            }

            2 -> {
                val binding = ItemGridBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BreedGridItemViewHolder(binding, breedsEventHandler)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BreedListItemViewHolder -> getItem(position)?.let { holder.onBind(it) }
            is BreedGridItemViewHolder -> getItem(position)?.let { holder.onBind(it) }
        }

    }

    override fun getItemViewType(position: Int): Int = if (isListView) 1 else 2
}