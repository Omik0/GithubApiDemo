package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home

import android.content.res.Configuration
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
        viewModel.getUserResponse("GithubApiDemo")

        binding.apply {
            adapter.addLoadStateListener { loadStates ->
                if (loadStates.append.endOfPaginationReached)
                    Snackbar.make(recyclerView, "End of paging", Snackbar.LENGTH_SHORT).show()
                when (loadStates.source.refresh) {
                    is LoadState.Loading -> {
                        binding.circularProgress.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.INVISIBLE
                    }
                    is LoadState.NotLoading -> {
                        binding.circularProgress.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                    }
                    is LoadState.Error -> {
                        Log.d("awdawdawd", "ErrorRefresh")
                        binding.circularProgress.visibility = View.GONE
                        binding.recyclerView.visibility = View.INVISIBLE
                    }
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
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    LinearLayoutManager(requireContext())
                else
                    GridLayoutManager(requireContext(), 2)
        }
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.adapter = adapter.withLoadStateFooter(
                footer = RepositoryLoadStateAdapter { adapter.retry() },
            )
        }

    }

}