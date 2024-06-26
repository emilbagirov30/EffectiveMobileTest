package com.emil.effectivemobiletest

import MainViewModel
import OfferAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emil.network.NetworkModule
import com.emil.network.ApiService
import com.emil.network.OffersResponse
import com.emil.ui.CapFragment

import com.squareup.moshi.Moshi
import com.emil.ui.R
import com.emil.ui.TicketsFragment


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var apiService: ApiService
    private var defaultColor:Int? = null
    private var buttonPressedColor:Int? = null
    private lateinit var ticketsButton:ImageButton
    private lateinit var ticketsText: TextView
    private lateinit var hotelsButton: ImageButton
    private lateinit var hotelsText: TextView
    private lateinit var shortButton: ImageButton
    private lateinit var shortText: TextView
    private lateinit var subscriptionsButton: ImageButton
    private lateinit var subscriptionsText: TextView
    private lateinit var profileButton: ImageButton
    private lateinit var profileText: TextView
    private lateinit var offerAdapter:OfferAdapter
    private lateinit var ticketsFragment:TicketsFragment
    private lateinit var  editor:SharedPreferences.Editor
private lateinit var  sharedPreferences: SharedPreferences
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val capFragment = CapFragment()
         ticketsFragment = TicketsFragment()
        defaultColor = ContextCompat.getColor(this, R.color.text)
        buttonPressedColor = ContextCompat.getColor(this, R.color.button_pressed)
        val moshi = NetworkModule().provideMoshi()
        val retrofit = NetworkModule().provideRetrofit(moshi)
        apiService = NetworkModule().provideApiService(retrofit)
        viewModel = ViewModelProvider(this, MainViewModelFactory(apiService)).get(MainViewModel::class.java)
        ticketsButton = findViewById(R.id.ib_tickets)
        ticketsText = findViewById(R.id.tv_tickets)
        hotelsButton = findViewById(R.id.ib_hotels)
        hotelsText = findViewById(R.id.tv_hotels)
        shortButton = findViewById(R.id.ib_short)
        shortText = findViewById(R.id.tv_short)
        subscriptionsButton = findViewById(R.id.ib_subscriptions)
        subscriptionsText = findViewById(R.id.tv_subscriptions)
        profileButton = findViewById(R.id.ib_profile)
        profileText = findViewById(R.id.tv_profile)
        val ticketsLl = findViewById<LinearLayout>(R.id.ll_tickets)
        val hotelsLl = findViewById<LinearLayout>(R.id.ll_hotels)
        val shortLl = findViewById<LinearLayout>(R.id.ll_short)
        val subscriptionsLl = findViewById<LinearLayout>(R.id.ll_subscriptions)
        val profileLl = findViewById<LinearLayout>(R.id.ll_profile)
        pressed(ticketsButton,ticketsText)
        changeFragment (ticketsFragment)

       ticketsLl.setOnClickListener{
            pressed(ticketsButton,ticketsText)
           changeFragment (ticketsFragment)
           viewModel.loadOffers()
        }
       hotelsLl.setOnClickListener{
            pressed(hotelsButton,hotelsText)
           changeFragment (capFragment)
        }
      shortLl.setOnClickListener{
            pressed(shortButton,shortText)
          changeFragment (capFragment)
        }
      subscriptionsLl.setOnClickListener{
            pressed(subscriptionsButton,subscriptionsText)
          changeFragment (capFragment)
        }
        profileLl.setOnClickListener{
            pressed(profileButton,profileText)
            changeFragment (capFragment)
        }
       sharedPreferences = getSharedPreferences("AppData", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.offersData.observe(this) { offers ->
            val fragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as? TicketsFragment
            fragment?.let {
                it.rvOffer.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                offerAdapter = OfferAdapter()
                it.rvOffer.adapter = offerAdapter
                offerAdapter.submitList(offers)
                val lastText = sharedPreferences.getString("where", "Минск").toString()
                println(lastText)
                ticketsFragment.whereEt.setText(lastText)

            }
        }
    }

    private fun pressed (ib: View, tv:TextView){
        ticketsButton.backgroundTintList = ColorStateList.valueOf(defaultColor!!)
        hotelsButton.backgroundTintList = ColorStateList.valueOf(defaultColor!!)
        shortButton.backgroundTintList = ColorStateList.valueOf(defaultColor!!)
        subscriptionsButton.backgroundTintList = ColorStateList.valueOf(defaultColor!!)
        profileButton.backgroundTintList = ColorStateList.valueOf(defaultColor!!)
        ticketsText.setTextColor(defaultColor!!)
        hotelsText.setTextColor(defaultColor!!)
        shortText.setTextColor(defaultColor!!)
        subscriptionsText.setTextColor(defaultColor!!)
        profileText.setTextColor(defaultColor!!)
        ib.backgroundTintList = ColorStateList.valueOf(buttonPressedColor!!)
        tv.setTextColor(buttonPressedColor!!)
    }


    private fun changeFragment (fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()

        }
    }

    override fun onStop() {
        super.onStop()
        val where = ticketsFragment.whereEt.text.trim().toString()
        editor.putString("where", where)
        editor.apply()
    }
}
