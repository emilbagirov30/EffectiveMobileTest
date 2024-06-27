package com.emil.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.emil.network.TicketOffer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BottomSheetDialog (private var where:String) : BottomSheetDialogFragment() {



    private lateinit var checkAllTickets: Button
    lateinit var whereEt:EditText
    lateinit var whitherEt:EditText

    companion object{
        var isFind = false
        const val TAG = "BottomSheetDialog"
        lateinit var  company1: TextView
        lateinit var  company2: TextView
        lateinit var  company3: TextView
        lateinit var price1: TextView
        lateinit var  price2: TextView
        lateinit var  price3: TextView
        lateinit var time1: TextView
        lateinit var time2: TextView
        lateinit var time3: TextView


    }
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
        val tips= view.findViewById<LinearLayout>(R.id.ll_tips)
        val backward= view.findViewById<LinearLayout>(R.id.ll_back)
        val dateLl= view.findViewById<LinearLayout>(R.id.ll_date)
        val ticketsLl= view.findViewById<LinearLayout>(R.id.ll_tickets)
        val cancel = view.findViewById<ImageButton>(R.id.ib_cancel)
        val revers = view.findViewById<ImageButton>(R.id.ib_reverse)
        val plane = view.findViewById<ImageView>(R.id.iv_plane_et)
        val search = view.findViewById<ImageView>(R.id.iv_search_et)
        val back = view.findViewById<ImageView>(R.id.iv_back)
        val loading = view.findViewById<ProgressBar>(R.id.pb_loading)
        val panel = view.findViewById<HorizontalScrollView>(R.id.hsv_panel)
        val date = view.findViewById<TextView>(R.id.tv_date)
        val dayOfTheWeek = view.findViewById<TextView>(R.id.tv_day_of_week)
        company1 =  view.findViewById(R.id.tv_c1)
        company2 =  view.findViewById(R.id.tv_c2)
        company3 =  view.findViewById(R.id.tv_c3)
        price1 =  view.findViewById(R.id.tv_price1)
        price2 =  view.findViewById(R.id.tv_price2)
        price3 =  view.findViewById(R.id.tv_price3)
       time1 =  view.findViewById(R.id.tv_time1)
        time2 =  view.findViewById(R.id.tv_time2)
        time3 =  view.findViewById(R.id.tv_time3)
        checkAllTickets =  view.findViewById(R.id.bt_check_all)
        setFormattedDate(date, dayOfTheWeek)


backward.setOnClickListener {
    showDatePickerDialog { date ->

    }
}

       dateLl.setOnClickListener {
           showDatePickerDialog { datePair ->
               date.text = datePair.first
             dayOfTheWeek.text = datePair.second
           }
        }
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

        cancel.setOnClickListener {
            whitherEt.setText("")
        }

        revers.setOnClickListener {
            val where = whereEt.text.toString().trim()
            val whither = whitherEt.text.toString().trim()
            whereEt.setText(whither)
            setTown(where)
        }
        back.setOnClickListener {
            dialog!!.cancel()
        }

       whitherEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentText = s.toString().trim()

                if (currentText.isNotEmpty()) {

                        revers.visibility = View.VISIBLE
                        plane.visibility = View.GONE
                        search.visibility = View.GONE
                        back.visibility = View.VISIBLE
                        tips.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                        panel.visibility = View.VISIBLE
                        ticketsLl.visibility = View.VISIBLE

                }else {
                    revers.visibility = View.GONE
                        plane.visibility = View.VISIBLE
                        search.visibility = View.VISIBLE
                        back.visibility = View.GONE
                        tips.visibility = View.VISIBLE
                        panel.visibility = View.GONE
                        ticketsLl.visibility = View.GONE
                        isFind = false

                }

            }

            override fun afterTextChanged(s: Editable?) {
lifecycleScope.launch(Dispatchers.IO){
    delay(2500)
    activity!!.runOnUiThread {
        loading.visibility = View.GONE
       }
    isFind = true
}
            }
        })


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

    private fun setFormattedDate(tv1: TextView, tv2: TextView) {
        val currentDate = Date()

        val dateFormat1 = SimpleDateFormat("d MMM, ", Locale.getDefault())
        val dateFormat2 = SimpleDateFormat("E", Locale.getDefault())

        val formattedDate1 = dateFormat1.format(currentDate)
        val formattedDate2 = dateFormat2.format(currentDate)

       tv1.text = "$formattedDate1"
        tv2.text = formattedDate2
    }
    private fun showDatePickerDialog(onDateSelected: (Pair<String, String>) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat1 = SimpleDateFormat("d MMM,", Locale.getDefault())
                val dateFormat2 = SimpleDateFormat("E", Locale.getDefault())

                val formattedDate1 = dateFormat1.format(selectedDate.time)
                val formattedDate2 = dateFormat2.format(selectedDate.time)

                onDateSelected(Pair(formattedDate1, formattedDate2))
            },
            year, month, day
        )
        datePickerDialog.show()
    }
    fun updateTicketOffers(ticketOffers: List<TicketOffer>) {
            company1.text = ticketOffers[0].title
            price1.text = "${ticketOffers[0].price.value} ₽"
            time1.text = ticketOffers[0].timeRange.joinToString(" ")

            company2.text = ticketOffers[1].title
            price2.text = "${ticketOffers[1].price.value} ₽"
            time2.text = ticketOffers[1].timeRange.joinToString(" ")

            company3.text = ticketOffers[2].title
            price3.text = "${ticketOffers[2].price.value} ₽"
            time3.text = ticketOffers[2].timeRange.joinToString(" ")

    }
}
