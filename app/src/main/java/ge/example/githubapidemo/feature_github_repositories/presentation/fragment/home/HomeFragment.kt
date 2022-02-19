package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ge.example.githubapidemo.databinding.FragmentHomeBinding
import ge.example.githubapidemo.feature_github_repositories.presentation.activity.MainViewModel
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.BaseFragment
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter.RepositoryAdapter
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter.RepositoryLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val adapter = RepositoryAdapter()
    private val viewModel: MainViewModel by viewModels()

    override fun start() {
        initRecycler()
        viewModel.getUserResponse("Github")

        binding.apply {

            adapter.addLoadStateListener { loadStates ->
                recyclerView.isVisible = loadStates.source.refresh is LoadState.NotLoading
                if (loadStates.source.refresh is LoadState.NotLoading &&
                    loadStates.append.endOfPaginationReached && adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                }
            }
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
    }


    private fun initRecycler() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
//                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
//                    GridLayoutManager(requireContext(), 2)
//                else
//                    GridLayoutManager(requireContext(), 4)
        }
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.adapter = adapter.withLoadStateFooter(
                footer = RepositoryLoadStateAdapter { adapter.retry() },
            )
        }

    }

}