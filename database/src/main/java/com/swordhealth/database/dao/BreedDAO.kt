package com.swordhealth.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.swordhealth.database.entities.BreedDTO

@Dao
interface BreedDAO {

    // INSERT

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBreeds(dataEntities: List<BreedDTO>)

    // GET

    @Transaction
    @Query("SELECT * FROM BreedDTO WHERE page = :page ORDER BY name ASC")
    suspend fun fetchBreedsByPage(page: Int): List<BreedDTO>

    @Transaction
    @Query("SELECT * FROM BreedDTO WHERE name LIKE :query ORDER BY name ASC")
    suspend fun fetchBreedsBySearchQuery(query: String): List<BreedDTO>

    @Transaction
    @Query("SELECT * FROM BreedDTO WHERE id =:id LIMIT 1")
    suspend fun fetchBreedByID(id: Int): BreedDTO
}