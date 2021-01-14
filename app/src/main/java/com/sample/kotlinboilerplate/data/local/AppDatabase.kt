package com.sample.kotlinboilerplate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.kotlinboilerplate.data.local.dao.UserDao
import com.sample.kotlinboilerplate.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}