package ge.example.githubapidemo.feature_github_repositories.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ge.example.githubapidemo.R
import ge.example.githubapidemo.databinding.ActivityMainBinding
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.details.DetailsFragmentDirections

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setUpBottomNavigation()
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
            R.id.detailsFragment -> {
                _binding.apply {
                    favouriteBtn.setImageDrawable(
                        ContextCompat.getDrawable(
                            favouriteBtn.context,
                            R.drawable.ic_arrow_back
                        )
                    )
                    searchBtn.setImageDrawable(
                        ContextCompat.getDrawable(
                            favouriteBtn.context,
                            R.drawable.ic_favorite_outlined
                        )
                    )
                    favouriteBtn.setOnClickListener {
                        findNavController(R.id.nav_host_fragment).navigateUp()
                    }
                }
            }
            R.id.homeFragment -> {
                _binding.apply {
                    favouriteBtn.setImageDrawable(
                        ContextCompat.getDrawable(
                            favouriteBtn.context,
                            R.drawable.ic_favorite_outlined
                        )
                    )
                    searchBtn.setImageDrawable(
                        ContextCompat.getDrawable(
                            favouriteBtn.context,
                            R.drawable.ic_filter
                        )
                    )
                }
            }
        }
    }
}