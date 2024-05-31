package com.namemanager
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "name_tb")
data class Name(@PrimaryKey @ColumnInfo(name = "name") val name: String)
