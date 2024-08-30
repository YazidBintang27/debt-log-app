package com.latihan.debtnote.screens

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.latihan.debtnote.MainActivity
import com.latihan.debtnote.R
import com.latihan.debtnote.database.entity.Debt
import com.latihan.debtnote.databinding.ActivityUpdateDataBinding
import com.latihan.debtnote.viewmodel.UpdateDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class UpdateDataActivity : AppCompatActivity() {

   companion object {
      const val EXTRA_DATA_INDEX = "data_index"
   }
   private lateinit var binding: ActivityUpdateDataBinding
   private val updateDataViewModel: UpdateDataViewModel by viewModels()
   private var debtId = 0

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      binding = ActivityUpdateDataBinding.inflate(layoutInflater)
      setContentView(binding.root)
      ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
         val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
         v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
         insets
      }
      observeData()
      setBorrowDate()
      setDueDate()
      updateData()
   }

   private fun observeData() {
      val index = intent.getIntExtra(EXTRA_DATA_INDEX, 0)
      val decimalFormat = DecimalFormat("#.000")
      lifecycleScope.launch {
         updateDataViewModel.getData(index)
         updateDataViewModel.debtData.collectLatest { data ->
            debtId = data.id
            binding.etName.setText(data.name)
            binding.etAmount.setText(decimalFormat.format(data.amount))
            binding.etBorrowDate.setText(data.borrowDate)
            binding.etDueDate.setText(data.dueData)
            if (data.paymentStatus) {
               binding.radioPaid.isChecked = true
            } else {
               binding.radioUnpaid.isChecked = true
            }
         }
      }
   }

   private fun setBorrowDate() {
      setDatePicker(binding.etBorrowDate)
   }

   private fun setDueDate() {
      setDatePicker(binding.etDueDate)
   }

   private fun updateData() {
      binding.btnUpdateData.setOnClickListener {
         val name = binding.etName.text.toString()
         val amount = binding.etAmount.text.toString().toDouble()
         val borrowDate = binding.etBorrowDate.text.toString()
         val dueDate = binding.etDueDate.text.toString()
         val paymentStatus = if (binding.radioPaid.isChecked) {
            true
         } else {
            false
         }
         val newDebtData = Debt(
            id = debtId,
            name = name,
            amount = amount,
            borrowDate = borrowDate,
            dueData = dueDate,
            paymentStatus = paymentStatus
         )
         lifecycleScope.launch {
            updateDataViewModel.updateData(newDebtData)
         }
         Log.d("update_data", newDebtData.toString())
         Log.d("update_data", binding.etName.text.toString())
         val intent = Intent(this@UpdateDataActivity, MainActivity::class.java)
         startActivity(intent)
         finish()
      }
   }

   private fun setDatePicker(editText: EditText) {
      editText.setOnClickListener {
         val calendar: Calendar = Calendar.getInstance()
         val year = calendar.get(Calendar.YEAR)
         val month = calendar.get(Calendar.MONTH)
         val day = calendar.get(Calendar.DAY_OF_MONTH)

         val datePickerDialog = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
               val cal = Calendar.getInstance()
               cal.set(year, month, dayOfMonth)
               val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
               editText.setText(dateFormat.format(cal.time))
            }, year, month, day
         )
         datePickerDialog.show()
      }
   }
}