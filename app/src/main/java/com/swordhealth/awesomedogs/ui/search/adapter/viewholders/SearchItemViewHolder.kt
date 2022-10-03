package com.swordhealth.awesomedogs.ui.search.adapter.viewholders

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.swordhealth.awesomedogs.databinding.ItemSearchBreedBinding
import com.swordhealth.awesomedogs.ui.BreedsEventHandler
import objects.BreedPresentation

class SearchItemViewHolder(
    private val view: ItemSearchBreedBinding,
    private val eventHandler: BreedsEventHandler
) : RecyclerView.ViewHolder(view.root) {

    fun onBind(breed: BreedPresentation) {

        view.tvBreedNameLabel.isVisible = !breed.name.isNullOrEmpty()
        view.tvBreedNameValue.isVisible = !breed.name.isNullOrEmpty()
        if (!breed.name.isNullOrEmpty())
            view.tvBreedNameValue.text = breed.name

        view.tvBreedGroupLabel.isVisible = !breed.group.isNullOrEmpty()
        view.tvBreedGroupValue.isVisible = !breed.group.isNullOrEmpty()
        if (!breed.group.isNullOrEmpty())
            view.tvBreedGroupValue.text = breed.group

        view.tvBreedOriginLabel.isVisible = !breed.origin.isNullOrEmpty()
        view.tvBreedOriginValue.isVisible = !breed.origin.isNullOrEmpty()
        if (!breed.origin.isNullOrEmpty())
            view.tvBreedOriginValue.text = breed.origin
    }
}