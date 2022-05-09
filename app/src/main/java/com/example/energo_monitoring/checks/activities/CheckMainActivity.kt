package com.example.energo_monitoring.checks.activities

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.energo_monitoring.compose.DrawerScreens
import com.example.energo_monitoring.compose.data.DestinationChangedCallback
import com.example.energo_monitoring.application.di.vm.ViewModelFactory
import com.example.energo_monitoring.checks.presenters.utilities.SharedPreferencesManager
import com.example.energo_monitoring.checks.viewmodel.GeneralInspectionViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class CheckMainActivity : AppCompatActivity() {

    var dataId: Int = 0
    private lateinit var binding: ActivityCheckMainBinding
    private lateinit var navController: NavController
    private var saveListeners = mutableListOf<DestinationChangedCallback>()
    var lastCreatedPath: Uri? = null
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var viewModel: GeneralInspectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityCheckMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[GeneralInspectionViewModel::class.java]

        dataId = intent.getIntExtra("dataId", 0)
        binding.toolbarText.text = intent.getStringExtra("clientName")

        navController = (supportFragmentManager.fragments[0] as NavHostFragment).navController

        val username = binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.username)
        username.text = SharedPreferencesManager.getUsername(this)

        configureToolbar()
        configureNavigationDrawer()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val currentDestination = controller.currentDestination?.id ?: 0
            val listener = saveListeners.firstOrNull { currentDestination == it.navigationId }
            if (listener != null) {
                listener.saveDataCallback()
            } else {
                Toast.makeText(this, "Данные не были сохранены!", Toast.LENGTH_LONG).show()
            }
        }

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

    fun insertDestinationListener(id: Int, callback: () -> Unit) {
        val listener = saveListeners.firstOrNull { it.navigationId == id }
        if (listener == null)
            saveListeners.add(DestinationChangedCallback(id, callback))
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