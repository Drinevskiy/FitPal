package by.drinevskiy.fitpal

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import by.drinevskiy.fitpal.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        NavigationUI.setupWithNavController(navView, navController)
        navView.setupWithNavController(navController)
//        navView.setOnN
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_purchase_detail, R.id.navigation_purchase -> {
                    navView.menu.findItem(R.id.navigation_profile)?.isChecked = true
                }
                R.id.navigation_fridge -> {
                    navView.menu.findItem(R.id.navigation_kitchen)?.isChecked = true
                }
                R.id.navigation_home,
                R.id.navigation_add_product,
                R.id.navigation_kitchen,
                R.id.navigation_profile -> {
                    navView.menu.findItem(destination.id)?.isChecked = true
                }
            }
        }
    }
}