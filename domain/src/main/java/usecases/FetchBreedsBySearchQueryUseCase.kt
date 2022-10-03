package usecases

import repository.BreedsRepositoryContract

class FetchBreedsBySearchQueryUseCase(private val breedsRepository: BreedsRepositoryContract) {
    suspend operator fun invoke(query: String) = breedsRepository.getBreedsBySearchQuery(query)
}