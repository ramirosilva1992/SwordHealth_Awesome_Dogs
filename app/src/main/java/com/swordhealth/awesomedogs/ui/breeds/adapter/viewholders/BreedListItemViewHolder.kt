package com.swordhealth.awesomedogs.ui.breeds.adapter.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.swordhealth.awesomedogs.databinding.ItemListBreedBinding
import com.swordhealth.awesomedogs.ui.BreedsEventHandler
import com.swordhealth.awesomedogs.utils.loadImage
import objects.BreedPresentation

class BreedListItemViewHolder(
    private val binding: ItemListBreedBinding,
    private val eventHandler: BreedsEventHandler
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(breed: BreedPresentation) {
        binding.tvBreedName.text = breed.name
        binding.ivBreedPicture.loadImage(breed.image)
        binding.root.setOnClickListener { eventHandler.onBreedClick(breed.id) }
    }
}