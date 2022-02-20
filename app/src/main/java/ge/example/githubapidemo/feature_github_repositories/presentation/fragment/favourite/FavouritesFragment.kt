package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.favourite

import android.content.res.Configuration
import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ge.example.githubapidemo.databinding.FragmentFavouritesBinding
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.BaseFragment
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.favourite.adapter.FavRepoAdapter
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.HomeFragmentDirections
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.HomeViewModel
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter.RepositoryAdapter
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter.RepositoryLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavouritesFragment :
    BaseFragment<FragmentFavouritesBinding>(FragmentFavouritesBinding::inflate) {
    private val favAdapter = FavRepoAdapter()
    private val favViewModel: FavouritesViewModel by viewModels()

    override fun start() {
        initRecycler()
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favViewModel.state.collectLatest {
                    d("awdawd", it.toString())
                    favAdapter.submitList(it)
                }
            }
        }
    }

    private fun initRecycler() {
        binding.favRv.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    LinearLayoutManager(requireContext())
                else
                    GridLayoutManager(requireContext(), 2)
            adapter = favAdapter
        }

        favAdapter.repoItemOnClick = { owner, repo ->
            findNavController().navigate(
                FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(
                    owner,
                    repo
                )
            )
        }
        favAdapter.deleteRepoItemOnClick = { id ->
            favViewModel.deleteRepoFromRoom(id)
            val oldList = mutableListOf<RepositoryItem>()
            oldList.addAll(favAdapter.currentList)
            favAdapter.submitList(oldList)
        }

    }

}