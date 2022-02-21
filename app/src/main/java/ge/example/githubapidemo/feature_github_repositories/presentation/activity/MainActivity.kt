package ge.example.githubapidemo.feature_github_repositories.presentation.activity

import android.os.Build
import android.os.Bundle
import android.util.Log.d
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ge.example.githubapidemo.R
import ge.example.githubapidemo.databinding.ActivityMainBinding
import ge.example.githubapidemo.extensions.gone
import ge.example.githubapidemo.extensions.setDrawable
import ge.example.githubapidemo.extensions.showSnack
import ge.example.githubapidemo.extensions.visible
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.HomeFragmentDirections
import ge.example.githubapidemo.utils.ConnectivityListener
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var _binding: ActivityMainBinding

    @Inject
    lateinit var connectivityListener: ConnectivityListener
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setUpBottomNavigation()

        connectivityListener.observe(this) { isNetworkAvailable ->
            if (!isNetworkAvailable)
                _binding.root.showSnack(getString(R.string.no_internet), R.color.errorRed)
        }

    }

    private fun setUpBottomNavigation() {
        _binding.run {
            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(
                this@MainActivity
            )
        }
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> {
                _binding.apply {
                    favouriteBtn.setDrawable(R.drawable.ic_favorite_outlined)
                    searchBtn.setDrawable(R.drawable.ic_filter)
                    searchBtn.visible()
                    favouriteBtn.setOnClickListener {
                        findNavController(R.id.nav_host_fragment).navigate(
                            HomeFragmentDirections.actionHomeFragmentToFavouritesFragment()
                        )
                    }
                }
            }
            R.id.detailsFragment -> {
                _binding.apply {
                    favouriteBtn.setDrawable(R.drawable.ic_arrow_back)
                    searchBtn.gone()
                    favouriteBtn.setOnClickListener {
                        findNavController(R.id.nav_host_fragment).navigateUp()
                    }
                }
            }
            R.id.favouritesFragment -> {
                _binding.apply {
                    favouriteBtn.setDrawable(R.drawable.ic_arrow_back)
                    searchBtn.setDrawable(R.drawable.ic_filter)
                    searchBtn.visible()
                    favouriteBtn.setOnClickListener {
                        findNavController(R.id.nav_host_fragment).navigateUp()
                    }
                }
            }
        }
    }


}