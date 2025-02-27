package com.example.appbackgroundchange.ui.theme

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbackgroundchange.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        val appList = listOf(
            AppModel(R.mipmap.ic_blinkit_alternate, "Blinkit", "IconBlinkit"),
            AppModel(R.mipmap.ic_shopsy_alternate, "Shopsy", "IconShopsy"),
            AppModel(R.mipmap.ic_zepto_alternate, "Zepto", "IconZepto"),
            AppModel(R.mipmap.ic_navi_alternate, "Navi", "IconNavi")
            )

        recyclerView.adapter = AppIconAdapter(this,appList)
    }
}
