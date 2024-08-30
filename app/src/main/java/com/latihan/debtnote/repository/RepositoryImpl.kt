package com.latihan.debtnote.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.latihan.debtnote.database.dao.DebtDao
import com.latihan.debtnote.database.entity.Debt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
   private val debtDao: DebtDao
): Repository {

   @WorkerThread
   override suspend fun insertData(debt: Debt) {
      debtDao.insertData(debt)
   }

   @WorkerThread
   override suspend fun updateData(debt: Debt) {
      debtDao.updateData(debt)
   }

   @WorkerThread
   override suspend fun deleteData(debt: Debt) {
      debtDao.deleteData(debt)
   }

   @WorkerThread
   override suspend fun getAllData(): Flow<List<Debt>> {
      return debtDao.getAllData()
   }
}