package com.swordhealth.awesomedogs.utils

import androidx.recyclerview.widget.DiffUtil
import objects.BreedPresentation

class BreedDiffCallback : DiffUtil.ItemCallback<BreedPresentation>() {

    override fun areItemsTheSame(oldItem: BreedPresentation, newItem: BreedPresentation): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BreedPresentation, newItem: BreedPresentation): Boolean =
        oldItem == newItem
}