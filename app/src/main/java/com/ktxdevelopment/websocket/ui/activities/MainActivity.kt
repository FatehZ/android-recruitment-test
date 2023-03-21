package com.ktxdevelopment.websocket.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ktxdevelopment.websocket.R
import com.ktxdevelopment.websocket.databinding.ActivityMainBinding
import com.ktxdevelopment.websocket.ui.fragments.FragmentMain
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        val fragmentMain = FragmentMain()
        supportFragmentManager.beginTransaction().add(R.id.navHost, fragmentMain).commit()
    }
}