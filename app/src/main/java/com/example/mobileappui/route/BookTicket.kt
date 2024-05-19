package com.example.mobileappui.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.mobileappui.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BookTicket : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        val nameEditText: EditText = view.findViewById(R.id.nameEditText)
        val phoneEditText: EditText = view.findViewById(R.id.phoneEditText)
        val ticketQuantityEditText: EditText = view.findViewById(R.id.ticketQuantityEditText)

        val buttonChangeFragment: Button = view.findViewById(R.id.paymentButton)
        buttonChangeFragment.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val ticketQuantity = ticketQuantityEditText.text.toString().toInt()
            replaceFragment(BookedTicket.newInstance(name, phone, ticketQuantity))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookTicket().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
