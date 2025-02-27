package com.example.appbackgroundchange.ui.theme

import com.google.android.gms.ads.nativead.NativeAd

data class FoodItem(
    val imageResId: Int = 0,
    val title: String = "",
    val subtitle: String = "",
    val nativeAd: NativeAd? = null
)
