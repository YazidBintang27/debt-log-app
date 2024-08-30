package com.latihan.debtnote.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.latihan.debtnote.database.entity.Debt
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertData(debt: Debt)

   @Update
   suspend fun updateData(debt: Debt)

   @Delete
   suspend fun deleteData(debt: Debt)

   @Query("SELECT * FROM debt_table")
   fun getAllData(): Flow<List<Debt>>
}