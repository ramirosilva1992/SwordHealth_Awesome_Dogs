package com.swordhealth.awesomedogs.ui.breeds.adapter.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.swordhealth.awesomedogs.databinding.ItemBreedBinding
import com.swordhealth.awesomedogs.ui.BreedsEventHandler
import com.swordhealth.awesomedogs.utils.loadImage
import objects.BreedPresentation

class BreedItemViewHolder(
    private val view: ItemBreedBinding,
    private val eventHandler: BreedsEventHandler
) : RecyclerView.ViewHolder(view.root) {

    fun onBind(breed: BreedPresentation) {
        view.tvBreedName.text = breed.name
        view.ivBreedPicture.loadImage(breed.image)
        view.root.setOnClickListener { eventHandler.onBreedClick(breed) }
    }
}