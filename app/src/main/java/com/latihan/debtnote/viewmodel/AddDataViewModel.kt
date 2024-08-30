package com.latihan.debtnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.debtnote.database.entity.Debt
import com.latihan.debtnote.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDataViewModel @Inject constructor(
   private val repository: RepositoryImpl
): ViewModel() {
   fun insertData(debt: Debt) {
      viewModelScope.launch(Dispatchers.IO) {
         repository.insertData(debt)
      }
   }
}