package com.gs.nasaapod.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gs.nasaapod.data.database.dao.FavouritePicturesDao
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity


@Database(
    entities = [FavouritePicturesEntity::class], version = 2, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var mInstance: AppDatabase? = null

        private const val DATABASE_NAME = "nasa_apod.db"

        fun getInstance(context: Context): AppDatabase {
            if (mInstance == null) {
                synchronized(this) {
                    if (mInstance == null) {
                        mInstance = Room.databaseBuilder(
                            context.applicationContext, AppDatabase::class.java, DATABASE_NAME
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return mInstance!!
        }

    }


    abstract fun favouritePicturesDao(): FavouritePicturesDao

}