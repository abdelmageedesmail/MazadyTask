package com.abdelmageed.mazadytask

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.abdelmageed.mazadytask.databinding.ActivityMainBinding
import com.abdelmageed.mazadytask.extension.visibility
import com.abdelmageed.mazadytask.util.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkMonitor = NetworkMonitor(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_auth) as NavHostFragment
        navController = navHostFragment.navController

        networkMonitor.isConnected.observe(this) { isConnected ->
            if (isConnected) {
                binding.constraintNoNetwork.visibility(false)

            } else {
                binding.constraintNoNetwork.visibility(true)
            }
        }
        networkMonitor.startMonitoring()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::networkMonitor.isInitialized)
            networkMonitor.stopMonitoring()
    }

}