package com.latihan.debtnote.repository

import androidx.lifecycle.LiveData
import com.latihan.debtnote.database.entity.Debt
import kotlinx.coroutines.flow.Flow

interface Repository {
   suspend fun insertData(debt: Debt)

   suspend fun updateData(debt: Debt)

   suspend fun deleteData(debt: Debt)

   suspend fun getAllData(): Flow<List<Debt>>
}