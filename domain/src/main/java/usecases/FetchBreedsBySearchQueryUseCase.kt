package usecases

import respositories.BreedsRepositoryContract

class FetchBreedsBySearchQueryUseCase(private val breedsRepository: BreedsRepositoryContract) {
    suspend operator fun invoke(query: String) = breedsRepository.getBreedsBySearchQuery(query)
}