package com.example.mobileappui.presentation.exhibition

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileappui.R
import com.example.mobileappui.models.AuthViewModel
import com.example.mobileappui.models.ExhibitionViewModel
import com.example.mobileappui.route.Home

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExhibitionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExhibitionFragment : Fragment() {
    private val exhibitionViewModel: ExhibitionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exhibition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backBtn = view.findViewById<Button>(R.id.back_btn)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        exhibitionViewModel.exhibitionItems.observe(viewLifecycleOwner, Observer { items ->
            recyclerView.adapter = items?.let { ExhibitionAdapter(it) { exhibition ->
                val bundle = Bundle().apply {
                    putString("name", exhibition.name)
                    putString("description", exhibition.description)
                    putString("image", exhibition.image)
                }

                val exhibitionItemFragment = ExhibitionItemFragment().apply {
                    arguments = bundle
                }
                Log.d("ExhibitionFragment", "click")
                replaceFragment(exhibitionItemFragment)
                }
            }
        })

        backBtn.setOnClickListener {
            replaceFragment(Home())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}