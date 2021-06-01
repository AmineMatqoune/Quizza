package com.example.quizza.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.quizza.entities.User

@Dao
interface UserDAO {
    @Insert
    fun insertNewUser(user: User)

    @Update
    fun updateScore(user: User)

    @Query("SELECT * FROM User WHERE username = (:username) and password = (:password)")        // it means that this function corresponds to a query (exactly the one
    fun checkLogin(username: String, password: String): User                                 // I've specified with the parameter "username")and it returns an object of type User
//
//    @Query("SELECT * FROM User WHERE username = username")
//    fun checkUserExists(username: String): Boolean
}