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

        MobileAds.initialize(this) {}

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val backArrow: ImageView = findViewById(R.id.back_arrow)
        backArrow.setOnClickListener {
            finish()
        }

        initFoodList()

        adapter = FoodAdapter(this, foodList)
        recyclerView.adapter = adapter

        loadNativeAds()
    }

    private fun initFoodList() {

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

        val adPositionsCount = (foodList.size / 3)

        val adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110") // Test ID
            .forNativeAd { nativeAd: NativeAd ->
                nativeAds.add(nativeAd)

                if (nativeAds.size == adPositionsCount) {
                    insertAdsIntoList()
                }
            }
            .withAdListener(object : com.google.android.gms.ads.AdListener() {
                override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {

                    if (nativeAds.isNotEmpty()) {
                        insertAdsIntoList()
                    }
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        for (i in 0 until adPositionsCount) {
            adLoader.loadAd(com.google.android.gms.ads.AdRequest.Builder().build())
        }
    }

    private fun insertAdsIntoList() {
        var adIndex = 0

        var i = 3
        while (i <= foodList.size && adIndex < nativeAds.size) {
            foodList.add(i, FoodItem(nativeAd = nativeAds[adIndex]))
            adIndex++
            i += 4
        }

        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        for (ad in nativeAds) {
            ad.destroy()
        }
        super.onDestroy()
    }
}