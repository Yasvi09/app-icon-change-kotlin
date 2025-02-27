package com.example.appbackgroundchange.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbackgroundchange.R
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.AdLoader

class FoodOrderActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_order)

        // Initialize AdMob
        MobileAds.initialize(this) {}

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val foodList = mutableListOf(
            FoodItem(R.drawable.food1, "Inchicapi", "Platos Fuertes"),
            FoodItem(R.drawable.food2, "Verduras Al Wok", "Platos Fuertes"),
            FoodItem(R.drawable.food3, "Zuchinil Con Arroz y Pesto", "Platos Fuertes")
        )

        // Load Native Ad
        val adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110") // Test ID
            .forNativeAd { nativeAd: NativeAd ->
                foodList.add(FoodItem(nativeAd = nativeAd)) // Add native ad to list
                adapter.notifyDataSetChanged()
            }
            .withAdListener(object : com.google.android.gms.ads.AdListener() {})
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        adLoader.loadAd(com.google.android.gms.ads.AdRequest.Builder().build())

        adapter = FoodAdapter(this, foodList)
        recyclerView.adapter = adapter
    }
}
