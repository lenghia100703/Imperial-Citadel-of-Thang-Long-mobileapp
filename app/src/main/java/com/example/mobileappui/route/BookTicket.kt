package com.example.mobileappui.route

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobileappui.MainActivity
import com.example.mobileappui.R
import com.example.mobileappui.models.TicketViewModel
import com.example.mobileappui.models.TransactionViewModel
import com.example.mobileappui.presentation.transaction.TransactionFragment

class BookTicket : Fragment() {
    private val transactionViewModel: TransactionViewModel by viewModels()
    private val ticketViewModel: TicketViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewTransaction: Button = view.findViewById(R.id.viewTransaction)
        val quantity: EditText = view.findViewById(R.id.quantity)
        val paymentButton: Button = view.findViewById(R.id.paymentButton)
        paymentButton.setOnClickListener {
            if (quantity.text.toString().toLongOrNull() != null) {
                transactionViewModel.createTransactionByUserId(
                    quantity.text.toString().toLongOrNull()!!,
                    {
                        Toast.makeText(
                            requireContext(),
                            "Tạo giao dịch thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    {
                        Toast.makeText(
                            requireContext(),
                            "Tạo giao dịch thất bại!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }

        viewTransaction.setOnClickListener {
            replaceFragment(TransactionFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
