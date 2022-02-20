package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ge.example.githubapidemo.R
import ge.example.githubapidemo.databinding.RepositoryItemLayoutBinding
import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse

typealias OnRepoClick = (owner: String, repo: String) -> Unit

class RepositoryAdapter :
    PagingDataAdapter<GithubResponse.RepositoryItem, RepositoryAdapter.ShortItemViewHolder>(
        REPOSITORY_COMPARATOR
    ) {

    var repoItemOnClick: OnRepoClick? = null

    companion object {
        private val REPOSITORY_COMPARATOR =
            object : DiffUtil.ItemCallback<GithubResponse.RepositoryItem>() {
                override fun areItemsTheSame(
                    oldItem: GithubResponse.RepositoryItem,
                    newItem: GithubResponse.RepositoryItem
                ) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: GithubResponse.RepositoryItem,
                    newItem: GithubResponse.RepositoryItem
                ) =
                    oldItem == newItem
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShortItemViewHolder(
            RepositoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ShortItemViewHolder, position: Int) {
        val currentRepo = getItem(position)
        holder.onBind(currentRepo)
    }

    inner class ShortItemViewHolder(private val binding: RepositoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun onBind(repo: GithubResponse.RepositoryItem?) {
            repo?.apply {
                binding.apply {
//                    ownerImage.setNetworkImage(owner?.avatarUrl)
                    repoNameTv.text = fullName
                    repoDescTv.text =
                        description ?: repoDescTv.context.getString(R.string.no_repo_desc)
                    languageTv.text = language ?: languageTv.context.getString(R.string.none)
                    startTv.text = stargazersCount.toString()
                    forksTv.text = forksCount.toString()
                }
            }
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val model = getItem(absoluteAdapterPosition)
            model?.let {
                repoItemOnClick?.invoke(
                    model.owner?.login ?: "Omik0",
                    model.name ?: "GithubApiDemo"
                )
            }
        }
    }


}