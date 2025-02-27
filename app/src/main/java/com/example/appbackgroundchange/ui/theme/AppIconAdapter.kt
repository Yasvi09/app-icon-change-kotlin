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
import android.widget.Toast
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
            changeAppIcon(context, app.aliasName)
        }
    }

    override fun getItemCount() = appList.size

    private fun changeAppIcon(context: Context, selectedAlias: String) {
        try {
            val packageManager = context.packageManager
            val packageName = context.packageName

            val mainActivity = ComponentName(packageName, "$packageName.ui.theme.MainActivity")

            val aliasComponents = mapOf(
                "IconBlinkit" to ComponentName(packageName, "$packageName.IconBlinkit"),
                "IconShopsy" to ComponentName(packageName, "$packageName.IconShopsy"),
                "IconZepto" to ComponentName(packageName, "$packageName.IconZepto"),
                "IconNavi" to ComponentName(packageName, "$packageName.IconNavi")
            )

            packageManager.setComponentEnabledSetting(
                mainActivity,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )

            aliasComponents.values.forEach { component ->
                packageManager.setComponentEnabledSetting(
                    component,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
                )
            }

            val selectedComponent = aliasComponents[selectedAlias]
            if (selectedComponent != null) {
                packageManager.setComponentEnabledSetting(
                    selectedComponent,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
                )

                Toast.makeText(context, "App icon changed. You may need to restart your device to see the change.", Toast.LENGTH_LONG).show()
            } else {

                packageManager.setComponentEnabledSetting(
                    mainActivity,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
                )

                Toast.makeText(context, "Failed to change app icon", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Error changing icon: ${e.message}", Toast.LENGTH_LONG).show()
        }

        (context as Activity).finishAffinity()
    }
}
