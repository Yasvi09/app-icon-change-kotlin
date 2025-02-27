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
import android.widget.ImageView
import android.view.View

class FoodOrderActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private val foodList = mutableListOf<FoodItem>()
    private val nativeAds = mutableListOf<NativeAd>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_order)

        // Initialize AdMob
        MobileAds.initialize(this) {}

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up back button
        val backArrow: ImageView = findViewById(R.id.back_arrow)
        backArrow.setOnClickListener {
            finish()
        }

        // Initialize our food list
        initFoodList()

        // Create adapter with initial food items (without ads)
        adapter = FoodAdapter(this, foodList)
        recyclerView.adapter = adapter

        // Load ads and insert them into the list
        loadNativeAds()
    }

    private fun initFoodList() {
        // Add sample food items (you can add more as needed)
        foodList.add(FoodItem(R.drawable.food_1, "Inchicapi", "Platos Fuertes"))
        foodList.add(FoodItem(R.drawable.food_2, "Verduras Al Wok", "Platos Fuertes"))
        foodList.add(FoodItem(R.drawable.food_3, "Ceviche", "Platos Fuertes"))
        foodList.add(FoodItem(R.drawable.food_4, "Arroz con Pollo", "Platos Fuertes"))
        foodList.add(FoodItem(R.drawable.food_5, "Chaufa de Pollo", "Platos Fuertes"))
        foodList.add(FoodItem(R.drawable.food_6, "Tallarines Rojos", "Platos Fuertes"))
        foodList.add(FoodItem(R.drawable.food_7, "Tallarines Verdes", "Platos Fuertes"))
        foodList.add(FoodItem(R.drawable.food_8, "Lomo Saltado", "Platos Fuertes"))
        foodList.add(FoodItem(R.drawable.food_9, "Aji de Gallina", "Platos Fuertes"))
        foodList.add(FoodItem(R.drawable.food_10, "Seco de Res", "Platos Fuertes"))
    }

    private fun loadNativeAds() {
        // Calculate how many ads we need based on food list size
        val adPositionsCount = (foodList.size / 3)

        val adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110") // Test ID
            .forNativeAd { nativeAd: NativeAd ->
                nativeAds.add(nativeAd)

                // Once we have all the ads we need, insert them into the list
                if (nativeAds.size == adPositionsCount) {
                    insertAdsIntoList()
                }
            }
            .withAdListener(object : com.google.android.gms.ads.AdListener() {
                override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {
                    // If ad loading fails, let's still try to insert what we have
                    if (nativeAds.isNotEmpty()) {
                        insertAdsIntoList()
                    }
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        // Load the number of ads we need
        for (i in 0 until adPositionsCount) {
            adLoader.loadAd(com.google.android.gms.ads.AdRequest.Builder().build())
        }
    }

    private fun insertAdsIntoList() {
        var adIndex = 0

        // Insert ads after every 3 food items
        var i = 3
        while (i <= foodList.size && adIndex < nativeAds.size) {
            foodList.add(i, FoodItem(nativeAd = nativeAds[adIndex]))
            adIndex++
            // Skip 3 more items (including the ad we just inserted)
            i += 4
        }

        // Notify adapter that data has changed
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        // Clean up native ads to prevent memory leaks
        for (ad in nativeAds) {
            ad.destroy()
        }
        super.onDestroy()
    }
}