package com.latihan.debtnote.di

import android.app.Application
import androidx.room.Room
import com.latihan.debtnote.database.DebtDatabase
import com.latihan.debtnote.database.dao.DebtDao
import com.latihan.debtnote.repository.Repository
import com.latihan.debtnote.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

   @Provides
   @Singleton
   fun provideDatabase(app: Application): DebtDatabase {
      return Room.databaseBuilder(
         app,
         DebtDatabase::class.java,
         "debt_note_db"
      ).build()
   }

   @Provides
   fun provideDebtDao(db: DebtDatabase): DebtDao {
      return db.debtDao
   }

   @Provides
   @Singleton
   fun provideRepository(db: DebtDatabase): Repository {
      return RepositoryImpl(db.debtDao)
   }
}