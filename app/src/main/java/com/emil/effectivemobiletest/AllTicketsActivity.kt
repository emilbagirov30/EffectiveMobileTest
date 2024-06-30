package com.emil.effectivemobiletest

import MainViewModel
import TicketsAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emil.network.ApiService
import com.emil.network.NetworkModule
import com.emil.ui.R
class AllTicketsActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var ticketsAdapter: TicketsAdapter
private lateinit var  loading:ProgressBar
    private lateinit var  flMain:FrameLayout
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tickets)

        val towns = findViewById<TextView>(R.id.tv_towns)
        val info = findViewById<TextView>(R.id.tv_user_info)
        val back = findViewById<ImageButton>(R.id.ib_back)
        val ticketList = findViewById<RecyclerView>(R.id.rv_list_all)
        loading = findViewById(R.id.pb_loading)
        flMain = findViewById(R.id.fl_main)
        val moshi = NetworkModule().provideMoshi()
        val retrofit = NetworkModule().provideRetrofit(moshi)
        val apiService = NetworkModule().provideApiService(retrofit)
        viewModel = ViewModelProvider(this, MainViewModelFactory(apiService)).get(MainViewModel::class.java)
        val where = intent.getStringExtra("where")
        val whither = intent.getStringExtra("whither")
        val date = intent.getStringExtra("date")
        val dateWithoutLastChar = date?.substring(0, date.length - 1)

        towns.text = "$where-$whither"
        info.text = dateWithoutLastChar

        back.setOnClickListener {
            onBackPressed()
        }

        ticketsAdapter = TicketsAdapter()
        ticketList.layoutManager = LinearLayoutManager(this)
        ticketList.adapter = ticketsAdapter

        observeViewModel()
        viewModel.loadTickets()
    }

    private fun observeViewModel() {
        viewModel.ticketsData.observe(this) { tickets ->
            val sortedList = tickets.sortedByDescending { it.badge != null }
            ticketsAdapter.submitList(sortedList)
            loading.visibility = View.GONE
            flMain.visibility = View.VISIBLE
        }
    }
}

