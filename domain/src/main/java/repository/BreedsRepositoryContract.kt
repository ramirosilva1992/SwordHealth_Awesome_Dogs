package repository

import com.swordhealth.api.Resource
import com.swordhealth.database.entities.BreedDTO
import kotlinx.coroutines.flow.Flow
import objects.BreedPresentation

interface BreedsRepositoryContract {
    suspend fun getBreedsByPage(page: Int): Flow<Resource<List<BreedPresentation>>>
    suspend fun getBreedsBySearchQuery(query: String): Flow<Resource<List<BreedPresentation>>>
}