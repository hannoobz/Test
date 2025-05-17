package com.hannoobz.internship

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val sharedPrefUsername = getSharedPreferences(getString(R.string.current_username), Context.MODE_PRIVATE)
        with(sharedPrefUsername.edit()){
            putString(getString(R.string.current_username),"")
            apply()
        }
        val sharedPrefSelectedUser= getSharedPreferences(getString(R.string.selected_username), Context.MODE_PRIVATE)
        with(sharedPrefSelectedUser.edit()){
            putString(getString(R.string.selected_username),"")
            apply()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}