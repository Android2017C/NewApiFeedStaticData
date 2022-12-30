package com.cnureddy.newapifeedstaticdata.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cnureddy.newapifeedstaticdata.adapter.NewsAdapter
import com.cnureddy.newapifeedstaticdata.common.Constants.isRegistered
import com.cnureddy.newapifeedstaticdata.common.Resource
import com.cnureddy.newapifeedstaticdata.databinding.ActivityMainBinding
import com.cnureddy.newapifeedstaticdata.model.ArticlesItem
import com.cnureddy.newapifeedstaticdata.util.NetworkConnectionMonitor
import com.cnureddy.newapifeedstaticdata.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class NewsMainActivity : AppCompatActivity() {
    private var connectionMonitor: NetworkConnectionMonitor? = null
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: ActivityMainBinding
    private val newsViewModel: NewsViewModel by viewModels()
    var leadList1: ArrayList<ArticlesItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()
        connectionMonitor = NetworkConnectionMonitor(applicationContext)
        connectionMonitor?.observe(this) { isConnected ->
            if (isConnected == true) {
                //DO void Online()
                newsViewModel.getNewList()
                lifecycleScope.launchWhenStarted {
                    newsViewModel.observeNewsStateFlow.collect {
                        when (it) {
                            is Resource.Loading -> {
                                showProgress()
                            }
                            is Resource.Error -> {
                                Log.d("main_Error", "onCreate: ${it.message}")

                                hideProgress()
                            }
                            is Resource.Success -> {
                                hideProgress()
                                val articlesItem = it.data?.articles as List<ArticlesItem>
                                leadList1.addAll(articlesItem)
                                newsAdapter.setArticlesData(articlesItem)
                                Log.d("main_success", "list size : : ${articlesItem.size}")
                                for (list in articlesItem) {

                                    Log.d("main_success", "onCreate: ${list.title}")
                                }
                                Log.d("main_success_op", "mylist test: ${leadList1}")

                            }
                            is Resource.Empty -> {
                                hideProgress()
                                Log.d("main_empty", "onCreate: ${it.toString()}")
                            }
                        }
                    }
                }
            } else {
                //DO void Offline();
                hideProgress()
                val snackbar =
                    Snackbar.make(
                        coordinatorLayout,
                        "offline make sure internet connection ON. and try again... ",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.setAction(
                    "UNDO"
                ) { Toast.makeText(applicationContext, "Undo action", Toast.LENGTH_SHORT).show() }
                snackbar.show()
            }
        }
        Log.d("main_success_p", "mylist test: ${leadList1}")

    }

    override fun onPause() {
        if (isRegistered) {
            connectionMonitor?.unregisterDefaultNetworkCallback()
            isRegistered = false
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectionMonitor?.registerDefaultNetworkCallback()
        };
        isRegistered = true;
    }

    private fun showProgress() {
        binding.progress.visibility = VISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = GONE
    }

    private fun initRecyclerview() {
        newsAdapter = NewsAdapter(applicationContext, ArrayList())
        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@NewsMainActivity)
            adapter = newsAdapter
        }
    }

}