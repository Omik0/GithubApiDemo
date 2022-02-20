package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.details

import android.util.Log.d
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ge.example.githubapidemo.databinding.FragmentDetailsBinding
import ge.example.githubapidemo.extensions.setNetworkImage
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val detailArgs: DetailsFragmentArgs by navArgs()

    override fun start() {
        detailArgs.apply {
            detailsViewModel.getRepo(owner, repo)
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.repoState.collectLatest { repoState ->
                    when (repoState) {
                        is Resource.Loading -> {
                            binding.apply {
                                rootConstraint.children.forEach {
                                    if (it.tag == "progress")
                                        it.visibility = View.VISIBLE
                                    else
                                        it.visibility = View.GONE
                                }
                            }
                        }
                        is Resource.Success -> {
                            binding.rootConstraint.children.forEach {
                                if (it.tag != "progress" && it.tag != "error")
                                    it.visibility = View.VISIBLE
                                else
                                    it.visibility = View.GONE
                            }
                            repoState.data?.let { repo ->
                                binding.apply {
                                    ownerTv.text = repo.owner?.login.plus("/")
                                    repoTv.text = repo.name
                                    ownerImage.setNetworkImage(repo.owner?.avatarUrl)
                                    repoDescTv.text = repo.description
                                    starValueTv.text = repo.stargazersCount.toString()
                                    forkValueTv.text = repo.forksCount.toString()
                                    issueValueTv.text = repo.openIssuesCount.toString()
                                    branchName.text = repo.defaultBranch
                                    LinkValueTv.text = repo.htmlUrl

                                }
                            }
                        }
                        is Resource.Error -> {
                            binding.apply {
                                rootConstraint.children.forEach {
                                    if (it.tag == "error")
                                        it.visibility = View.VISIBLE
                                    else
                                        it.visibility = View.GONE
                                }
                                errorTv.text = repoState.message
                            }
                        }
                    }
                }
            }
        }
    }
}