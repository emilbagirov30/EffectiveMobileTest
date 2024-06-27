package com.emil.ui

import OfferAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TicketsFragment : Fragment() {

    lateinit var rvOffer: RecyclerView
   lateinit var whereEt: EditText
    lateinit var whitherEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tickets, container, false)
        rvOffer = view.findViewById(R.id.rv_list_tickets)
        whereEt = view.findViewById(R.id.et_where)
        whitherEt = view.findViewById(R.id.et_whither)




        return view
    }



}