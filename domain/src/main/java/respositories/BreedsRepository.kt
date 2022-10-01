package respositories

import asBreedDTOList
import com.swordhealth.api.datasource.BreedsRemoteDataSource
import com.swordhealth.database.dao.BreedDAO
import com.swordhealth.database.entities.BreedDTO
import kotlinx.coroutines.flow.Flow

class BreedsRepository(
    private val breedDAO: BreedDAO,
    private val breedsRemoteDataSource: BreedsRemoteDataSource
) : BreedsRepositoryContract {

    override suspend fun getBreedsByPage(page: Int): Flow<List<BreedDTO>> {
        breedsRemoteDataSource.getBreedsByPage(page).collect { breedsList ->
            breedDAO.insertAllBreeds(breedsList.asBreedDTOList(page))
        }

        return breedDAO.fetchBreedsByPage(page)
    }

    override suspend fun getBreedsBySearchQuery(query: String): Flow<List<BreedDTO>> {
        breedsRemoteDataSource.getBreedsBySearchQuery(query).collect { breedsList ->
            breedDAO.insertAllBreeds(breedsList.asBreedDTOList(0))
        }

        return breedDAO.fetchBreedsBySearchQuery(query)
    }

}