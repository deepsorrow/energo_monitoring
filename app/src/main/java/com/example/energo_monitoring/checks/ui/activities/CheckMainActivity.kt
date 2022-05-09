package com.example.energo_monitoring.checks.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.activities.CreatingNew1Activity
import com.example.energo_monitoring.databinding.ActivityCheckMainBinding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.energo_monitoring.EnergomonitoringApplication
import com.example.energo_monitoring.checks.di.DaggerCheckComponent
import com.example.energo_monitoring.compose.DrawerScreens
import com.example.energo_monitoring.compose.data.DestinationChangedCallback
import com.example.energo_monitoring.checks.ui.presenters.utilities.SharedPreferencesManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CheckMainActivity : AppCompatActivity(), HasAndroidInjector {

    var dataId: Int = 0
    private lateinit var binding: ActivityCheckMainBinding
    private lateinit var navController: NavController
    private var saveListeners = mutableListOf<DestinationChangedCallback>()
    var lastCreatedPath: Uri? = null
    var lastCreatedPathReal: String = ""
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataId = intent.getIntExtra("dataId", 0)

        val component = DaggerCheckComponent.builder()
            .activity(this)
            .appComponent((application as EnergomonitoringApplication).appComponent)
            .build()

        component.inject(this)

        binding = ActivityCheckMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbarText(intent.getStringExtra("clientName").orEmpty())

        navController = (supportFragmentManager.fragments[0] as NavHostFragment).navController

        val username = binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.username)
        username.text = SharedPreferencesManager.getUsername(this)

        configureToolbar()
        configureNavigationDrawer()

        binding.bottomNavView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return true
    }

    fun setToolbarText(text: String) {
        binding.toolbarText.text = text
    }

    private fun configureToolbar() {
        setSupportActionBar(binding.toolbar)
        val actionbar: ActionBar? = supportActionBar
        if (actionbar != null) {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun configureNavigationDrawer() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            val intent = Intent(this, CreatingNew1Activity::class.java)
            val itemId = menuItem.itemId
            if (itemId == R.id.checks) {
                startActivity(intent)
            } else if (itemId == R.id.catalog) {
                intent.putExtra("route", DrawerScreens.Sync.route)
                startActivity(intent)
            }
            false
        }
    }
}