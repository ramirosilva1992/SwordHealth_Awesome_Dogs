package repository

import android.content.Context
import asBreedDTOList
import asBreedPresentation
import asBreedPresentationList
import com.swordhealth.api.APIService
import com.swordhealth.api.Resource
import com.swordhealth.api.datasource.BreedsRemoteDataSource
import com.swordhealth.api.objects.Breed
import com.swordhealth.database.DogDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import objects.BreedPresentation

class BreedsRepository(
    private val dogDatabase: DogDatabase,
    private val breedsRemoteDataSource: BreedsRemoteDataSource
) : BreedsRepositoryContract {

    override suspend fun getBreedsByPage(page: Int): Flow<Resource<List<BreedPresentation>>> =
        flow {
            emit(Resource.Loading())

            val resource = breedsRemoteDataSource.getBreedsByPage(page)

            if (!resource.data.isNullOrEmpty())
                dogDatabase.breedDAO().insertAllBreeds((resource.data as List<Breed>).asBreedDTOList(page))

            val breedsList = dogDatabase.breedDAO().fetchBreedsByPage(page)

            if (breedsList.isEmpty() && !resource.message.isNullOrEmpty())
                emit(Resource.Error(resource.message!!))
            else
                emit(Resource.Success(data = breedsList.asBreedPresentationList(), resource.totalBreeds))
        }

    override suspend fun getBreedsBySearchQuery(query: String): Flow<Resource<List<BreedPresentation>>> =
        flow {
            emit(Resource.Loading())

            val resource = breedsRemoteDataSource.getBreedsBySearchQuery(query)

            if (!resource.data.isNullOrEmpty())
                dogDatabase.breedDAO().insertAllBreeds((resource.data as List<Breed>).asBreedDTOList(0))

            val breedsList = dogDatabase.breedDAO().fetchBreedsBySearchQuery("%$query%")

            if (breedsList.isEmpty() && !resource.message.isNullOrEmpty())
                emit(Resource.Error(resource.message!!))
            else
                emit(Resource.Success(data = breedsList.asBreedPresentationList()))
        }

    override suspend fun getBreedByID(id: Int): BreedPresentation = dogDatabase.breedDAO().fetchBreedByID(id).asBreedPresentation()

    companion object {
        private var INSTANCE: BreedsRepository? = null

        operator fun invoke(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: BreedsRepository(DogDatabase(context), BreedsRemoteDataSource(APIService.provideAPIService())).also {
                    INSTANCE = it
                }
            }
    }
}