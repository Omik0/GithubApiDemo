package ge.example.githubapidemo.feature_github_repositories.presentation.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ge.example.githubapidemo.BuildConfig
import ge.example.githubapidemo.Keys
import ge.example.githubapidemo.R
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customizeSplashScreen(splashScreen)

    }

    private fun customizeSplashScreen(splashScreen: SplashScreen) {
        keepSplashScreenLonger(splashScreen)
        customizeSplashScreenExit(splashScreen)
    }

    private fun keepSplashScreenLonger(splashScreen: SplashScreen) {
//        splashScreen.setKeepOnScreenCondition {
//            !viewModel.isDataReady
//        }
    }

    private fun customizeSplashScreenExit(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->

//            showSplashExitAnimator(splashScreenViewProvider.view) {
                splashScreenViewProvider.remove()
//            }

//            showSplashIconExitAnimator(splashScreenViewProvider.iconView) {
//                splashScreenViewProvider.remove()
//            }
        }
    }

    private fun showSplashExitAnimator(splashScreenView: View, onExit: () -> Unit = {}) {

        val scaleOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0f, 0f)
            }
        )
        AnimatorSet().run {
            duration = resources.getInteger(R.integer.splash_exit_total_duration).toLong()
            interpolator = AnticipateInterpolator()
            playTogether(scaleOut)

            doOnEnd {
                onExit()
            }
            start()
        }
    }


    private fun showSplashIconExitAnimator(iconView: View, onExit: () -> Unit = {}) {

        val scaleOut = ObjectAnimator.ofFloat(
            iconView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0.3f, 0.3f)
            }
        )

        AnimatorSet().run {
            interpolator = AnticipateInterpolator()
            duration =
                resources.getInteger(R.integer.splash_exit_total_duration)
                    .toLong()

            playTogether(scaleOut)
            doOnEnd {
                onExit()
            }
            start()
        }
    }

}