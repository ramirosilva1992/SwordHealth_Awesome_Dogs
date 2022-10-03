package com.swordhealth.awesomedogs.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swordhealth.awesomedogs.databinding.ItemSearchBreedBinding
import com.swordhealth.awesomedogs.ui.BreedsEventHandler
import com.swordhealth.awesomedogs.ui.search.adapter.viewholders.SearchItemViewHolder
import com.swordhealth.awesomedogs.utils.BreedDiffCallback
import objects.BreedPresentation

class SearchAdapter(
    private val breedsEventHandler: BreedsEventHandler
) : ListAdapter<BreedPresentation, RecyclerView.ViewHolder>(
    BreedDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSearchBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchItemViewHolder(binding, breedsEventHandler)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as SearchItemViewHolder).onBind(it) }
    }

}