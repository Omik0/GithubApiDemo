package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home

import android.util.Log.d
import ge.example.githubapidemo.databinding.FragmentHomeBinding
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun start() {
        d("TAGGGGGG", "Home Fragment")
    }

}