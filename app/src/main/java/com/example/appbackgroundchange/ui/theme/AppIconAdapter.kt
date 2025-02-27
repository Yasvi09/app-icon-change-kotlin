package com.example.appbackgroundchange.ui.theme

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appbackgroundchange.R

class AppIconAdapter(private val context: Context, private val appList: List<AppModel>) :
    RecyclerView.Adapter<AppIconAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appIcon: ImageView = view.findViewById(R.id.appIcon)
        val appName: TextView = view.findViewById(R.id.appName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.app_icon_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = appList[position]
        holder.appIcon.setImageResource(app.icon)
        holder.appName.text = app.name

        holder.itemView.setOnClickListener {
            changeAppIcon(context, position)
        }
    }

    override fun getItemCount() = appList.size

    private fun changeAppIcon(context: Context, position: Int) {
        val packageManager = context.packageManager

        val aliases = listOf(
            "com.example.appbackgroundchange.IconBlinkit",
            "com.example.appbackgroundchange.IconShopsy",
            "com.example.appbackgroundchange.IconZepto"
        )

        for (alias in aliases) {
            packageManager.setComponentEnabledSetting(
                ComponentName(context, alias),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }

        if (position < aliases.size) {
            packageManager.setComponentEnabledSetting(
                ComponentName(context, aliases[position]),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }

        (context as Activity).finishAffinity()
    }
}
