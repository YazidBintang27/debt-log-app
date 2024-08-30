package com.latihan.debtnote.viewmodel

import android.util.Log
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
class UpdateDataViewModel @Inject constructor(
   private val repository: RepositoryImpl
): ViewModel() {

   private var _debtData = MutableStateFlow(Debt())
   val debtData: StateFlow<Debt>
      get() = _debtData.asStateFlow()

   fun getData(index: Int) {
      viewModelScope.launch(Dispatchers.IO) {
         repository.getAllData().collectLatest { data ->
            _debtData.tryEmit(data[index])
         }
      }
   }

   fun updateData(debt: Debt) {
      viewModelScope.launch(Dispatchers.IO) {
         repository.updateData(debt)
         Log.d("update_data", debt.toString())
      }
   }
}