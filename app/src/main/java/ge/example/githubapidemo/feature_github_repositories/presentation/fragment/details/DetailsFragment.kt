package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.details

import android.content.Intent
import android.net.Uri
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
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
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
    }

    private fun setChipStyle(): ChipDrawable {
        return ChipDrawable.createFromAttributes(
            requireContext(),
            null,
            0,
            com.google.android.material.R.style.Widget_MaterialComponents_Chip_Action

        ).apply {
            chipBackgroundColor =
                AppCompatResources.getColorStateList(requireContext(), R.color.primaryVariant)
        }
    }

    private fun createTopicChips(topic: String): Chip {
        return Chip(requireContext()).apply {
            text = topic
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(requireContext(), R.color.primaryDarkVariant))
            setChipDrawable(setChipStyle())
        }
    }


    override fun observer() {
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

    private fun setUpViews(repo: GithubResponse.RepositoryItem) {
        binding.apply {
            ownerTv.text = repo.owner?.login.plus("/")
            repoTv.text = repo.name
//          ownerImage.setNetworkImage(repo.owner?.avatarUrl)
            repoDescTv.text = repo.description
            starValueTv.text = repo.stargazersCount.toString()
            forkValueTv.text = repo.forksCount.toString()
            issueValueTv.text = repo.openIssuesCount.toString()
            branchName.text = repo.defaultBranch
            LinkValueTv.text = repo.htmlUrl
            topicChipGroup.removeAllViews()
            repo.topics?.forEach {
                topicChipGroup.addView(createTopicChips(it))
            }
        }
    }

    private fun openLink(address: String) {
        val uri: Uri = Uri.parse(address)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }


}