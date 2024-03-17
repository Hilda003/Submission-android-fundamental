package com.example.submission_android_fundamental.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_android_fundamental.R
import com.example.submission_android_fundamental.UserFavoriteActivity
import com.example.submission_android_fundamental.data.adapter.MainAdapter
import com.example.submission_android_fundamental.data.response.UserResult
import com.example.submission_android_fundamental.data.viewmodel.MainViewModel
import com.example.submission_android_fundamental.databinding.ActivityMainBinding
import com.example.submission_android_fundamental.utils.Help

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val context = this
    private val user = ArrayList<UserResult>()
    private val viewModel: MainViewModel by viewModels()
    private val adapter = MainAdapter(context)

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvUser.layoutManager = llm
        binding.rvUser.adapter = adapter

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        userSearch()

        viewModel.apply {
            loading.observe(context) {
                binding.progressBar.visibility = it
            }
            error.observe(context) {
                Help.toast(context, it)
            }
            result.observe(context) {
                adapter.apply {
                    if (it.isNullOrEmpty()) {
                        clearData()

                    } else {
                        initData(it)
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }
    private fun userSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                query.let {
                    binding.rvUser.visibility = View.VISIBLE
                    viewModel.searchResults(it)
                    userData(user)
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.rvUser.windowToken, 0)

                binding.TextHint.visibility = View.GONE
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun userData(username: List<UserResult>) {
       val user = ArrayList<UserResult>()
        for(result in username){
            user.clear()
            user.addAll(username)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite-> {
                val intent = Intent(this@MainActivity, UserFavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_setting -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }

            else -> {

                super.onOptionsItemSelected(item)
            }
        }
    }
}