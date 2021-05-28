package com.example.quizza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.quizza.entities.*
import com.example.quizza.dao.*

@Database(entities = [User::class], version = 1)
abstract class DBManager(): RoomDatabase(){

    //This class adopts Singleton GoF
    companion object {
        private var dbInstance: DBManager? = null

        fun getInstance(appContext: Context): DBManager{
            if (dbInstance == null)
                dbInstance = Room.databaseBuilder(appContext, DBManager::class.java, appContext.getString(R.string.users_db_name))
                    .createFromAsset(appContext.getString(R.string.users_db_name)).build()

            return dbInstance as DBManager
        }
    }

    abstract fun userDAO(): UserDAO
}