package com.latihan.debtnote.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.latihan.debtnote.R
import com.latihan.debtnote.database.entity.Debt
import com.latihan.debtnote.databinding.DebtCardBinding
import java.text.DecimalFormat

class DebtCardAdapter: RecyclerView.Adapter<DebtCardAdapter.ViewHolder>() {

   private var debtData: List<Debt> = listOf()
   private lateinit var onItemClickCallback: OnItemClickCallback
   private lateinit var onDeleteClickCallback: OnDeleteClickCallback

   interface OnItemClickCallback {
      fun onItemClicked(index: Int)
   }

   interface OnDeleteClickCallback {
      fun onDeleteClicked(debt: Debt)
   }

   fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
      this.onItemClickCallback = onItemClickCallback
   }

   fun setOnDeleteCallback(onDeleteClickCallback: OnDeleteClickCallback) {
      this.onDeleteClickCallback = onDeleteClickCallback
   }

   class ViewHolder(val binding: DebtCardBinding): RecyclerView.ViewHolder(binding.root)

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding = DebtCardBinding.inflate(layoutInflater, parent, false)
      return ViewHolder(binding)
   }

   override fun getItemCount(): Int {
      return debtData.size
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val context = holder.itemView.context
      val decimalFormat = DecimalFormat("#.000")
      val amountFormatted = decimalFormat.format(debtData[position].amount)
      holder.binding.tvName.text = context.getString(R.string.debt_name, debtData[position].name)
      holder.binding.tvMoney.text = context.getString(R.string.debt_amount, amountFormatted)
      holder.binding.tvPeminjaman.text = context.getString(R.string.borrow_date, debtData[position].borrowDate)
      holder.binding.tvJatuhTempo.text = context.getString(R.string.due_date, debtData[position].dueData)
      holder.binding.tvStatus.text = if (debtData[position].paymentStatus) {
         context.getString(R.string.payment_status_paid)
      } else {
         context.getString(R.string.payment_status_unpaid)
      }
      holder.itemView.setOnClickListener {
         onItemClickCallback.onItemClicked(position)
      }
      holder.binding.icDelete.setOnClickListener {
         onDeleteClickCallback.onDeleteClicked(debtData[position])
      }
   }

   @SuppressLint("NotifyDataSetChanged")
   fun setData(data: List<Debt>) {
      this.debtData = data
      notifyDataSetChanged()
   }
}