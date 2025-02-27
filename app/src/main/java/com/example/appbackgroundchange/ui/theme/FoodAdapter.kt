package com.example.appbackgroundchange.ui.theme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appbackgroundchange.R
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

class FoodAdapter(private val context: Context, private val itemList: List<FoodItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_TYPE_FOOD = 0
    private val ITEM_TYPE_AD = 1

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position].nativeAd != null) ITEM_TYPE_AD else ITEM_TYPE_FOOD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_FOOD) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
            FoodViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.item_native_ad, parent, false)
            AdViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FoodViewHolder) {
            val item = itemList[position]
            holder.foodImage.setImageResource(item.imageResId)
            holder.foodTitle.text = item.title
            holder.foodSubtitle.text = item.subtitle
        } else if (holder is AdViewHolder) {
            val item = itemList[position]
            val nativeAd = item.nativeAd
            nativeAd?.let { holder.bind(it) }
        }
    }

    override fun getItemCount(): Int = itemList.size

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage: ImageView = view.findViewById(R.id.foodImage)
        val foodTitle: TextView = view.findViewById(R.id.foodTitle)
        val foodSubtitle: TextView = view.findViewById(R.id.foodSubtitle)
    }

    class AdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val adView: NativeAdView = view.findViewById(R.id.nativeAdView)

        fun bind(nativeAd: NativeAd) {
            adView.headlineView = adView.findViewById(R.id.ad_headline)
            (adView.headlineView as TextView).text = nativeAd.headline

            adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
            (adView.callToActionView as TextView).text = nativeAd.callToAction

            adView.setNativeAd(nativeAd)
        }
    }
}
