package com.example.submission_android_fundamental.ui

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submission_android_fundamental.Settings
import com.example.submission_android_fundamental.data.viewmodel.SettingViewModel
import com.example.submission_android_fundamental.databinding.ActivitySettingBinding


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val pref = Settings.getInstance(dataStore)
        val setViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        setViewModel.getThemeApp().observe(this){
            when (it) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.themeLight.isChecked = true
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.themeDark.isChecked = true
                }

            }
        }
        binding.themeContainer.setOnCheckedChangeListener{
            _, id ->
            when (id) {
                binding.themeLight.id -> {
                    setViewModel.saveThemeApp(0)
                }
                binding.themeDark.id -> {
                    setViewModel.saveThemeApp(1)
                }
            }
        }
    }


  class ViewModelFactory(private val pref: Settings) : ViewModelProvider.NewInstanceFactory() {

      @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
                return SettingViewModel(pref) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)


        }
    }

}