package com.latihan.debtnote.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debt_table")
data class Debt(
   @PrimaryKey(autoGenerate = true)
   var id: Int = 0,

   @ColumnInfo(name = "name")
   var name: String = "",

   @ColumnInfo(name = "amount")
   var amount: Double = 0.000,

   @ColumnInfo(name = "borrow_date")
   var borrowDate: String = "",

   @ColumnInfo(name = "due_date")
   var dueData: String = "",

   @ColumnInfo(name = "payment_status")
   var paymentStatus: Boolean = false
)
