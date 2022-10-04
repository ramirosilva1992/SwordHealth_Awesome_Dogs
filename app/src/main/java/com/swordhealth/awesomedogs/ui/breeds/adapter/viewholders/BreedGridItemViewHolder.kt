package com.swordhealth.awesomedogs.ui.breeds.adapter.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.swordhealth.awesomedogs.databinding.ItemGridBreedBinding
import com.swordhealth.awesomedogs.ui.BreedsEventHandler
import com.swordhealth.awesomedogs.utils.loadImage
import objects.BreedPresentation

class BreedGridItemViewHolder(
    private val binding: ItemGridBreedBinding,
    private val eventHandler: BreedsEventHandler
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(breed: BreedPresentation) {
        binding.tvBreedName.text = breed.name
        binding.ivBreedPicture.loadImage(breed.image)
        binding.root.setOnClickListener { eventHandler.onBreedClick(breed.id) }
    }
}