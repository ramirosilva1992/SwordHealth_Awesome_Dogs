package com.swordhealth.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.swordhealth.database.dao.BreedDAO
import com.swordhealth.database.entities.BreedDTO
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BreedDbTests {

    private val database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DogDatabase::class.java)
        .allowMainThreadQueries().build()

    private lateinit var dao: BreedDAO

    @Before
    fun setup() {
        dao = database.breedDAO()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `when inserting breeds should return correct values by id`() = runTest {
        val expected = mockListOfBreeds()

        dao.insertAllBreeds(mockListOfBreeds())

        assertEquals(expected[0].name, dao.fetchBreedByID(1).name)
        assertEquals(expected[1].name, dao.fetchBreedByID(2).name)
        assertEquals(expected[2].name, dao.fetchBreedByID(3).name)
        assertEquals(expected[3].name, dao.fetchBreedByID(4).name)
    }

    @Test
    fun `when inserting breeds should return correct values by page`() = runTest {
        val expected = mockListOfBreeds()

        dao.insertAllBreeds(mockListOfBreeds())

        assertEquals(expected[0].name, dao.fetchBreedsByPage(1)[0].name)
        assertEquals(expected[1].name, dao.fetchBreedsByPage(1)[1].name)
        assertEquals(expected[2].name, dao.fetchBreedsByPage(2)[0].name)
        assertEquals(expected[3].name, dao.fetchBreedsByPage(2)[1].name)
    }

    @Test
    fun `when inserting breeds should return correct value by search query`() = runTest {
        val expected = mockListOfBreeds()

        dao.insertAllBreeds(mockListOfBreeds())

        assertEquals(expected[2].name, dao.fetchBreedsBySearchQuery("%name3%")[0].name)
    }

    private fun mockListOfBreeds() = listOf(
        BreedDTO(
            1,
            "dog_name1",
            "dog_category",
            "dog_group",
            "dog_origin",
            "dog_temperament",
            "dog_image",
            1
        ),
        BreedDTO(
            2,
            "dog_name2",
            "dog_category",
            "dog_group",
            "dog_origin",
            "dog_temperament",
            "dog_image",
            1
        ),
        BreedDTO(
            3,
            "dog_name3",
            "dog_category",
            "dog_group",
            "dog_origin",
            "dog_temperament",
            "dog_image",
            2
        ),
        BreedDTO(
            4,
            "dog_name4",
            "dog_category",
            "dog_group",
            "dog_origin",
            "dog_temperament",
            "dog_image",
            2
        )
    )
}