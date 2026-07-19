package com.desager.steamtraderkeys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.desager.steamtraderkeys.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupStatusBarBackground()

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.toolbar.setupWithNavController(navController)
    }

    private fun setupStatusBarBackground() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val statusBarHeight = insets
                .getInsets(WindowInsetsCompat.Type.statusBars())
                .top
            val navigationBarHeight = insets
                .getInsets(WindowInsetsCompat.Type.navigationBars())
                .bottom

            binding.statusBarBackground.updateLayoutParams {
                height = statusBarHeight
            }
            binding.navigationBarBackground.updateLayoutParams {
                height = navigationBarHeight
            }

            insets
        }
    }
}