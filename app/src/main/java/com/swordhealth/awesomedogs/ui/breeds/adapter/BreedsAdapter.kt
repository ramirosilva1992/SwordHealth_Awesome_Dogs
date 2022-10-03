package com.swordhealth.awesomedogs.ui.breeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swordhealth.awesomedogs.databinding.ItemBreedBinding
import com.swordhealth.awesomedogs.ui.BreedsEventHandler
import com.swordhealth.awesomedogs.ui.breeds.adapter.viewholders.BreedItemViewHolder
import com.swordhealth.awesomedogs.utils.BreedDiffCallback
import objects.BreedPresentation

class BreedsAdapter(
    private val breedsEventHandler: BreedsEventHandler
) : ListAdapter<BreedPresentation, RecyclerView.ViewHolder>(
    BreedDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedItemViewHolder(binding, breedsEventHandler)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as BreedItemViewHolder).onBind(it) }
    }
}