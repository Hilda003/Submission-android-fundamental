package com.example.submission_android_fundamental.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.submission_android_fundamental.Settings
import com.example.submission_android_fundamental.data.viewmodel.SettingViewModel
import com.example.submission_android_fundamental.databinding.ActivitySplashScreenBinding
import java.util.Timer
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val pref = Settings.getInstance(dataStore)
        val setViewModel = ViewModelProvider(this, SettingActivity.ViewModelFactory(pref))[SettingViewModel::class.java]

        setViewModel.getThemeApp().observe(this){
            when (it) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Timer().schedule(2000) {
            startActivity(Intent(
                this@SplashScreen,
                MainActivity::class.java

            ))
            finish()
        }
    }
}