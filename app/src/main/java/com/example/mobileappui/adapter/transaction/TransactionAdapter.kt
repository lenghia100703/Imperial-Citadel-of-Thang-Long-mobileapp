package com.example.mobileappui.adapter.transaction

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileappui.R
import com.example.mobileappui.dtos.transaction.TransactionDto

class TransactionAdapter(private val items: List<TransactionDto>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.description)
        val paymentMethod: TextView = itemView.findViewById(R.id.idTVPaymentMethod)
        val quantity: TextView = itemView.findViewById(R.id.idTVNumberOfPeople)
        val totalPrice: TextView = itemView.findViewById(R.id.idTVTotalPrice)
        val status: TextView = itemView.findViewById(R.id.idTVTransactionStatus)
        val createAt: TextView = itemView.findViewById(R.id.idTVTransactionDate)
        @SuppressLint("SetTextI18n")
        fun bind(transaction: TransactionDto) {
            id.text = "Mã giao dịch: " + transaction.id
            paymentMethod.text = "Phương thức thanh toán: " +  transaction.paymentMethod
            quantity.text = "Số lượng người: " + transaction.quantity
            totalPrice.text = "Thành tiền: " + transaction.totalPrice + " USD"
            status.text = "Trạng thái: " + transaction.status
            createAt.text = "Ngày tạo: " + transaction.createdAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}