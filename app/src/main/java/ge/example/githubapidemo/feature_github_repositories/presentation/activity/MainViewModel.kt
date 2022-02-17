package ge.example.githubapidemo.feature_github_repositories.presentation.activity

import android.os.SystemClock
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    companion object {
        const val WORK_DURATION = 1000L
    }
    private val initTime = SystemClock.uptimeMillis()
    fun isDataReady() = SystemClock.uptimeMillis() - initTime > WORK_DURATION
}