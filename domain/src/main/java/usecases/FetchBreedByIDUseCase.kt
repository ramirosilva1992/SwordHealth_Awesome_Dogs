package usecases

import repository.BreedsRepositoryContract

class FetchBreedByIDUseCase(private val breedsRepository: BreedsRepositoryContract) {
    suspend operator fun invoke(id: Int) = breedsRepository.getBreedByID(id)
}