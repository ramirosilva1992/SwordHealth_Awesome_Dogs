package usecases

import respositories.BreedsRepositoryContract

class FetchBreedsByPageUseCase(private val breedsRepository: BreedsRepositoryContract) {
    suspend operator fun invoke(page: Int) = breedsRepository.getBreedsByPage(page)
}