package com.example.appbackgroundchange.ui.theme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appbackgroundchange.R
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

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
            // Apply rounded corners to the food image
            val drawable = ContextCompat.getDrawable(context, item.imageResId)
            if (drawable != null) {
                holder.foodImage.setImageResource(item.imageResId)
                // We're keeping the rounded_image.xml as the background which gives the rounded effect
            }
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
        private val adIcon: ImageView = view.findViewById(R.id.ad_app_icon)

        fun bind(nativeAd: NativeAd) {
            // Set the headline text
            adView.headlineView = adView.findViewById(R.id.ad_headline)
            (adView.headlineView as TextView).text = nativeAd.headline

            // Set the call to action
            adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
            (adView.callToActionView as TextView).text = nativeAd.callToAction

            // Set the app icon image
            adView.iconView = adIcon
            val icon = nativeAd.icon
            if (icon != null) {
                adIcon.visibility = View.VISIBLE
                adIcon.setImageDrawable(icon.drawable)
            } else {
                adIcon.visibility = View.GONE
            }

            // Register the native ad view
            adView.setNativeAd(nativeAd)
        }
    }
}