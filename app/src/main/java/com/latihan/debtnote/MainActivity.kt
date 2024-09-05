package com.latihan.debtnote

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihan.debtnote.adapter.DebtCardAdapter
import com.latihan.debtnote.database.entity.Debt
import com.latihan.debtnote.databinding.ActivityMainBinding
import com.latihan.debtnote.screens.AddDataActivity
import com.latihan.debtnote.screens.UpdateDataActivity
import com.latihan.debtnote.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.appcompat.widget.SearchView.OnQueryTextListener

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding
   private val homeViewModel: HomeViewModel by viewModels()
   private val debtCardAdapter: DebtCardAdapter = DebtCardAdapter()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)
      ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
         val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
         v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
         insets
      }
      setupActionBar()
      setupAdapter()
      getAllData()
      addData()
      deleteData()
      searchData()
   }

   private fun setupActionBar() {
      setSupportActionBar(binding.myToolbar)
   }

   private fun setupAdapter() {
      binding.rvDebtCard.apply {
         adapter = debtCardAdapter
         setHasFixedSize(true)
         layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      }
      debtCardAdapter.setOnClickCallback(object: DebtCardAdapter.OnItemClickCallback {
         override fun onItemClicked(index: Int) {
            val intent = Intent(this@MainActivity, UpdateDataActivity::class.java)
            intent.putExtra(UpdateDataActivity.EXTRA_DATA_INDEX, index)
            startActivity(intent)
         }
      })
   }

   private fun getAllData() {
      lifecycleScope.launch {
         homeViewModel.allData.collectLatest {
            debtCardAdapter.setData(it)
         }
      }
   }

   private fun addData() {
      binding.fabAddData.setOnClickListener {
         val intent = Intent(this, AddDataActivity::class.java)
         startActivity(intent)
      }
   }

   private fun deleteData() {
      debtCardAdapter.setOnDeleteCallback(object: DebtCardAdapter.OnDeleteClickCallback {
         override fun onDeleteClicked(debt: Debt) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("Apakah anda yakin menghapus data ini?")
               .setPositiveButton("Ya") { dialog, id ->
                  homeViewModel.deleteData(debt)
               }
               .setNegativeButton("Batal") { dialog, id ->
                  dialog.dismiss()
               }
            builder.create().show()
         }
      })
   }

   private fun searchData() {
      binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
         OnQueryTextListener {
         override fun onQueryTextSubmit(query: String?): Boolean {
            return false
         }

         override fun onQueryTextChange(newText: String?): Boolean {
            val listData = homeViewModel.allData.value
            val filteredData = listData.filter {
               it.name.contains(newText ?: "", ignoreCase = true)
            }
            debtCardAdapter.setData(filteredData)
            return true
         }
      })
   }
}