package com.swordhealth.awesomedogs.ui

import objects.BreedPresentation

interface BreedsEventHandler {
    fun onBreedClick(breed: BreedPresentation)
}