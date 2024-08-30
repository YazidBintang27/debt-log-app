package com.latihan.debtnote.screens

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.latihan.debtnote.MainActivity
import com.latihan.debtnote.R
import com.latihan.debtnote.database.entity.Debt
import com.latihan.debtnote.databinding.ActivityAddDataBinding
import com.latihan.debtnote.viewmodel.AddDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddDataActivity : AppCompatActivity() {

   private lateinit var binding: ActivityAddDataBinding
   private val addDataViewModel: AddDataViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      binding = ActivityAddDataBinding.inflate(layoutInflater)
      setContentView(binding.root)
      ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
         val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
         v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
         insets
      }
      setupActionBar()
      setBorrowDate()
      setDueDate()
      addData()
   }

   private fun setupActionBar() {
      setSupportActionBar(binding.myToolbar)
   }

   private fun setBorrowDate() {
      setDatePicker(binding.etBorrowDate)
   }

   private fun setDueDate() {
      setDatePicker(binding.etDueDate)
   }

   private fun setDatePicker(editText: EditText) {
      editText.setOnClickListener {
         val calendar: Calendar = Calendar.getInstance()
         val nowYear = calendar.get(Calendar.YEAR)
         val nowMonth = calendar.get(Calendar.MONTH)
         val nowDay = calendar.get(Calendar.DAY_OF_MONTH)

         val datePickerDialog = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
               val cal = Calendar.getInstance()
               cal.set(year, month, dayOfMonth)
               val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
               editText.setText(dateFormat.format(cal.time))
            }, nowYear, nowMonth, nowDay)
         datePickerDialog.show()
      }
   }

   private fun addData() {
      binding.btnAddData.setOnClickListener {
         try {
            val name = binding.etName.text.toString()
            val amount = binding.etAmount.text.toString().toDouble()
            val borrowDate = binding.etBorrowDate.text.toString()
            val dueDate = binding.etDueDate.text.toString()
            if (name.isEmpty() || amount == 0.0 || borrowDate.isEmpty() || dueDate.isEmpty()) {
               Toast.makeText(this, "Isi seluruh form dengan benar", Toast.LENGTH_SHORT).show()
            } else {
               val debt =  Debt(
                  name = name,
                  amount = amount,
                  borrowDate = borrowDate,
                  dueData = dueDate
               )
               lifecycleScope.launch {
                  addDataViewModel.insertData(debt)
               }
               Log.d("add_data", debt.toString())
               val intent = Intent(this, MainActivity::class.java)
               startActivity(intent)
               finish()
            }
         } catch (e: NumberFormatException) {
            Toast.makeText(this, "Data yang anda masukkan tidak valid", Toast.LENGTH_SHORT).show()
         }
      }
   }
}