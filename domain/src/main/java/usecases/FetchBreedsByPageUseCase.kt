package usecases

import repository.BreedsRepositoryContract

class FetchBreedsByPageUseCase(private val breedsRepository: BreedsRepositoryContract) {
    suspend operator fun invoke(page: Int) = breedsRepository.getBreedsByPage(page)
}