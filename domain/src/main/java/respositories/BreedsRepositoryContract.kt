package respositories

import com.swordhealth.database.entities.BreedDTO
import kotlinx.coroutines.flow.Flow


interface BreedsRepositoryContract {
    suspend fun getBreedsByPage(page: Int): Flow<List<BreedDTO>>
    suspend fun getBreedsBySearchQuery(query: String): Flow<List<BreedDTO>>
}