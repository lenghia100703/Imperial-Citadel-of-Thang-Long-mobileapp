package com.example.mobileappui.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobileappui.R

private const val ARG_NAME = "name"
private const val ARG_PHONE = "phone"
private const val ARG_TICKET_QUANTITY = "ticket_quantity"

class BookedTicket : Fragment() {
    private var name: String? = null
    private var phone: String? = null
    private var ticketQuantity: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
            phone = it.getString(ARG_PHONE)
            ticketQuantity = it.getInt(ARG_TICKET_QUANTITY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booked_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val phoneTextView: TextView = view.findViewById(R.id.phoneTextView)
        val ticketQuantityTextView: TextView = view.findViewById(R.id.ticketQuantityTextView)

        nameTextView.text = name
        phoneTextView.text = phone
        ticketQuantityTextView.text = ticketQuantity.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, phone: String, ticketQuantity: Int) =
            BookedTicket().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_PHONE, phone)
                    putInt(ARG_TICKET_QUANTITY, ticketQuantity)
                }
            }
    }
}
