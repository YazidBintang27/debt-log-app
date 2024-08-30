package com.latihan.debtnote.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.latihan.debtnote.database.dao.DebtDao
import com.latihan.debtnote.database.entity.Debt

@Database(entities = [Debt::class], version = 1, exportSchema = false)
abstract class DebtDatabase: RoomDatabase() {
   abstract val debtDao: DebtDao
}