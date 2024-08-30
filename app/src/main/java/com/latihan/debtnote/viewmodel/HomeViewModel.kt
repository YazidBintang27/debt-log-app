package com.latihan.debtnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.debtnote.database.entity.Debt
import com.latihan.debtnote.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val repository: RepositoryImpl
): ViewModel() {

   private var _allData = MutableStateFlow(emptyList<Debt>())
   val allData: StateFlow<List<Debt>> = _allData.asStateFlow()

   init {
      getAllData()
   }

   private fun getAllData() {
      viewModelScope.launch(Dispatchers.IO) {
         repository.getAllData().collectLatest {
            _allData.tryEmit(it)
         }
      }
   }

   fun deleteData(debt: Debt) {
      viewModelScope.launch {
         repository.deleteData(debt)
      }
   }
}