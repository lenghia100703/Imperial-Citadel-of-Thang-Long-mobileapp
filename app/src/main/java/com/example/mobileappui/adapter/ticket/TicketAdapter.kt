package com.example.mobileappui.adapter.ticket

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileappui.R
import com.example.mobileappui.adapter.transaction.TransactionAdapter
import com.example.mobileappui.dtos.ticket.TicketDto
import com.example.mobileappui.dtos.transaction.TransactionDto

class TicketAdapter (private val items: List<TicketDto>) :
    RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {
    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.idTVPaymentMethod)
        val totalPrice: TextView = itemView.findViewById(R.id.idTVTotalPrice)
        val expiry: TextView = itemView.findViewById(R.id.idTVTransactionStatus)
        val createAt: TextView = itemView.findViewById(R.id.idTVTransactionDate)
        @SuppressLint("SetTextI18n")
        fun bind(ticket: TicketDto) {
            id.text = "Mã vé: " +  ticket.id
            totalPrice.text = "Tổng tiền: " + ticket.totalPrice + " USD"
            expiry.text = "Ngày hết hạn: " + ticket.expiry
            createAt.text = "Ngày đặt: " + ticket.createdAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_ticket_item, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}