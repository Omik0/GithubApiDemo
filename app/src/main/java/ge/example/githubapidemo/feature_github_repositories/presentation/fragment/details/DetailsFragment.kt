package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.details

import android.content.Intent
import android.net.Uri
import android.util.Log.d
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dagger.hilt.android.AndroidEntryPoint
import ge.example.githubapidemo.R
import ge.example.githubapidemo.databinding.FragmentDetailsBinding
import ge.example.githubapidemo.extensions.*
import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import ge.example.githubapidemo.feature_github_repositories.presentation.activity.MainActivity
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val detailArgs: DetailsFragmentArgs by navArgs()

    override fun start() {
        binding.apply {
            layRepoLink.setOnClickListener {
                openLink(LinkValueTv.text.toString())
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            detailArgs.apply {
                detailsViewModel.getRepo(owner, repo)
            }
        }
        detailArgs.apply {
            detailsViewModel.getRepo(owner, repo)
        }
        binding.ibIssue.setOnClickListener {
            detailsViewModel.addRepoInDatabase(detailsViewModel.repoState.value.data!!)
        }

        binding.fab.setOnClickListener {
            detailsViewModel.apply {
                repoState.value.data?.let { repo ->
                    if (repoExistsState.value) {
                        deleteRepoFromRoom(repo)

                    } else {
                        addRepoInDatabase(repo)
                    }
                    changeValue()
                }
            }
        }
    }

    override fun observer() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.repoExistsState.collectLatest { exists ->
                    if (exists) {
                        binding.fab.setDrawable(R.drawable.ic_favorite_filled)
                    } else {
                        binding.fab.setDrawable(R.drawable.ic_favorite_outlined)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.repoState.collectLatest { repoState ->
                    when (repoState) {
                        is Resource.Loading -> {
                            binding.apply {
                                rootConstraint.hideAll()
                                swipeRefresh.isRefreshing = true
                                errorTv.gone()
                            }
                        }
                        is Resource.Success -> {
                            binding.swipeRefresh.isRefreshing = false
                            binding.rootConstraint.children.forEach {
                                if (it.tag != "error")
                                    it.visible()
                                else
                                    it.gone()
                            }
                            repoState.data?.let { repo ->
                                detailsViewModel.checkRepoIfExists(repo)
                                setUpViews(repo)
                            }
                        }
                        is Resource.Error -> {
                            binding.apply {
                                rootConstraint.hideAll()
                                errorTv.text = repoState.message
                                swipeRefresh.isRefreshing = false
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpViews(repo: RepositoryItem) {
        binding.apply {
            ownerTv.text = repo.owner?.ownerLogin.plus("/")
            repoTv.text = repo.name
//          ownerImage.setNetworkImage(repo.owner?.avatarUrl)
            repoDescTv.text = repo.description
            starValueTv.text = repo.stargazersCount?.getFormattedNumber()
            forkValueTv.text = repo.forksCount?.getFormattedNumber()
            issueValueTv.text = repo.openIssuesCount?.getFormattedNumber()
            branchName.text = repo.defaultBranch
            LinkValueTv.text = repo.htmlUrl
        }
    }

    private fun openLink(address: String) {
        val uri: Uri = Uri.parse(address)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }


}