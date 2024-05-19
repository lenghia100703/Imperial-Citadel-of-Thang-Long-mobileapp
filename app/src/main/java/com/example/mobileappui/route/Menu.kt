package com.example.mobileappui.route

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mobileappui.R
import com.example.mobileappui.presentation.exhibition.ExhibitionFragment
import com.example.mobileappui.presentation.home.MainScreen
import com.example.mobileappui.presentation.home.dropletButtons
import com.example.mobileappui.presentation.ticket.TicketFragment
import com.example.mobileappui.presentation.transaction.TransactionFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [Menu.newInstance] factory method to
 * create an instance of this fragment.
 */
class Menu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val btnQnA = view.findViewById<Button>(R.id.btnQnA)
        btnQnA.setOnClickListener {
            replaceFragment(QnAScreen())
            val sendData = Intent(activity, MainScreen::class.java).apply {
                putExtra("openMenu", false)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            Log.d("NavBar", "Putting 'openMenu' into Intent")
            startActivity(sendData)
        }
        val btnReview = view.findViewById<Button>(R.id.btnReview)
        btnReview.setOnClickListener {
            replaceFragment(ReviewList(0))
            val sendData = Intent(activity, MainScreen::class.java).apply {
                putExtra("openMenu", false)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(sendData)
        }
        val btnBuilding = view.findViewById<Button>(R.id.btnBuilding)
        btnBuilding.setOnClickListener {
            replaceFragment(ViewLocation())
            val sendData = Intent(activity, MainScreen::class.java).apply {
                putExtra("openMenu", false)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(sendData)
        }
        val btn3D = view.findViewById<Button>(R.id.btn3D)
        btn3D.setOnClickListener {
            replaceFragment(ExhibitionFragment())
            val sendData = Intent(activity, MainScreen::class.java).apply {
                putExtra("openMenu", false)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            Log.d("NavBar", "Putting 'openMenu' into Intent")
            startActivity(sendData)
        }

        val btnViewTransaction = view.findViewById<Button>(R.id.viewTransaction)
        btnViewTransaction.setOnClickListener {
            replaceFragment(TicketFragment())
            val sendData = Intent(activity, MainScreen::class.java).apply {
                putExtra("openMenu", false)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            Log.d("NavBar", "Putting 'openMenu' into Intent")
            startActivity(sendData)
        }

        val btnViewTicket = view.findViewById<Button>(R.id.ticketQuantityTextView)
        btnViewTicket.setOnClickListener {
            replaceFragment(TransactionFragment())
            val sendData = Intent(activity, MainScreen::class.java).apply {
                putExtra("openMenu", false)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            Log.d("NavBar", "Putting 'openMenu' into Intent")
            startActivity(sendData)
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Menu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Menu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}