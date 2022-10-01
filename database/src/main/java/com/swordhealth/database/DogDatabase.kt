package com.swordhealth.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.swordhealth.database.dao.BreedDAO
import com.swordhealth.database.entities.BreedDTO

@Database(entities = [BreedDTO::class], version = 1)
abstract class DogDatabase : RoomDatabase() {

    abstract fun breedDAO(): BreedDAO

    companion object {

        @Volatile
        private var INSTANCE: DogDatabase? = null

        operator fun invoke(context: Context) =
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dog_database"
        ).build()
    }
}