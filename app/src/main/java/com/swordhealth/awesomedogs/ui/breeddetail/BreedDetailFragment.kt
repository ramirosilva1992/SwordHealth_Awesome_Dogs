package com.swordhealth.awesomedogs.ui.breeddetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.swordhealth.awesomedogs.databinding.FragmentBreedDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import objects.BreedPresentation
import repository.BreedsRepository
import usecases.FetchBreedByIDUseCase

class BreedDetailFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBreedDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var dialog: BottomSheetDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBreedDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog as BottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            arguments?.getInt(BREED_ID)?.let {
                renderScreen(FetchBreedByIDUseCase(BreedsRepository(requireContext()))(it))
            }
        }
    }

    private fun renderScreen(breed: BreedPresentation) {
        binding.tvBreedNameLabel.isVisible = !breed.name.isNullOrEmpty()
        binding.tvBreedNameValue.isVisible = !breed.name.isNullOrEmpty()
        if (!breed.name.isNullOrEmpty())
            binding.tvBreedNameValue.text = breed.name

        binding.tvBreedCategoryLabel.isVisible = !breed.category.isNullOrEmpty()
        binding.tvBreedCategoryValue.isVisible = !breed.category.isNullOrEmpty()
        if (!breed.category.isNullOrEmpty())
            binding.tvBreedCategoryValue.text = breed.category

        binding.tvBreedOriginLabel.isVisible = !breed.origin.isNullOrEmpty()
        binding.tvBreedOriginValue.isVisible = !breed.origin.isNullOrEmpty()
        if (!breed.origin.isNullOrEmpty())
            binding.tvBreedOriginValue.text = breed.origin

        binding.tvBreedTemperamentLabel.isVisible = !breed.temperament.isNullOrEmpty()
        binding.tvBreedTemperamentValue.isVisible = !breed.temperament.isNullOrEmpty()
        if (!breed.temperament.isNullOrEmpty())
            binding.tvBreedTemperamentValue.text = breed.temperament
    }

    override fun onDestroy() {
        dialog?.dismiss()
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val BREED_ID = "breed_id"
    }
}