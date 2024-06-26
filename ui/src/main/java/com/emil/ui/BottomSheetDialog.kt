package com.emil.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog (private var where:String) : BottomSheetDialogFragment() {
private lateinit var whereEt:EditText
    private lateinit var whitherEt:EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.bottom_sheet_layout,  container, false)
        whereEt = view.findViewById(R.id.et_where)
        whereEt.setText(where)
        whitherEt = view.findViewById(R.id.et_whither)
        val route = view.findViewById<ImageView>(R.id.iv_route)
        val anywhere = view.findViewById<ImageView>(R.id.iv_anywhere)
        val weekend = view.findViewById<ImageView>(R.id.iv_weekend)
        val hotTickets = view.findViewById<ImageView>(R.id.iv_hot_tickets)
        val stambul = view.findViewById<LinearLayout>(R.id.ll_stambul)
        val sochi = view.findViewById<LinearLayout>(R.id.ll_sochi)
        val phuket = view.findViewById<LinearLayout>(R.id.ll_phuket)

        route.setOnClickListener {
            switchToCapActivity()
        }
        weekend.setOnClickListener {
            switchToCapActivity()
        }
       hotTickets.setOnClickListener {
            switchToCapActivity()
        }
        anywhere.setOnClickListener {
            setTown("Куда угодно")
        }
        stambul.setOnClickListener {
            setTown("Стамбул")
        }
       sochi.setOnClickListener {
            setTown("Сочи")
        }
       phuket.setOnClickListener {
            setTown("Пхукет")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as? com.google.android.material.bottomsheet.BottomSheetDialog
            val bottomSheet = bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

        }
    }

    private fun switchToCapActivity() {
        val intent = Intent(activity, CapActivity::class.java)
        startActivity(intent)
    }


    private fun setTown (town:String){
        whitherEt.setText(town)
        whitherEt.setSelection(whitherEt.text.length)
        whitherEt.requestFocus()
    }
}
