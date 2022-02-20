package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ge.example.githubapidemo.R
import ge.example.githubapidemo.databinding.RepositoryItemLayoutBinding
import ge.example.githubapidemo.extensions.getFormattedNumber
import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem

typealias OnRepoClick = (owner: String, repo: String) -> Unit

class RepositoryAdapter :
    PagingDataAdapter<RepositoryItem, RepositoryAdapter.ShortItemViewHolder>(
        REPOSITORY_COMPARATOR
    ) {

    var repoItemOnClick: OnRepoClick? = null

    companion object {
        private val REPOSITORY_COMPARATOR =
            object : DiffUtil.ItemCallback<RepositoryItem>() {
                override fun areItemsTheSame(
                    oldItem: RepositoryItem,
                    newItem: RepositoryItem
                ) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: RepositoryItem,
                    newItem: RepositoryItem
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

        fun onBind(repo: RepositoryItem?) {
            repo?.apply {
                binding.apply {
//                    ownerImage.setNetworkImage(owner?.avatarUrl)
                    repoNameTv.text = fullName
                    repoDescTv.text =
                        description ?: repoDescTv.context.getString(R.string.no_repo_desc)
                    languageTv.text = language ?: languageTv.context.getString(R.string.none)
                    startTv.text = stargazersCount?.getFormattedNumber()
                    forksTv.text = forksCount?.getFormattedNumber()
                }
            }
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val model = getItem(absoluteAdapterPosition)
            model?.let {
                repoItemOnClick?.invoke(
                    model.owner?.ownerLogin ?: "Omik0",
                    model.name ?: "GithubApiDemo"
                )
            }
        }
    }


}