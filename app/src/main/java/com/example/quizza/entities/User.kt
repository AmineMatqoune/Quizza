package com.example.quizza.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class User{

    @PrimaryKey
    lateinit var username: String

    @ColumnInfo(name="password")
    lateinit var password: String

    @ColumnInfo(name="email")
    lateinit var email: String

    @ColumnInfo(name="avatar")
    lateinit var avatar: String

    @ColumnInfo(name="totalScore")
    var totalScore = 0
}