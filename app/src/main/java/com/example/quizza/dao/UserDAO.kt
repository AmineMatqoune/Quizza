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

    @Query("SELECT * FROM User WHERE username = (:username) and password = (:password)")        // it means that this function corresponds to the upper query
    fun checkLogin(username: String, password: String): User

    @Query("SELECT * FROM User WHERE username = (:username)")
    fun checkUserExists(username: String): User

    @Query("UPDATE User SET totalScore = (:totalScore) WHERE username = (:username)")
    fun updateScore(username: String, totalScore: Int)
}