package com.example.ekacareassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "information_table")
data class Information(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val age: Int,
    val dateOfBirth: String,
    val address: String
)
