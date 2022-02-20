package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.favourite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ge.example.githubapidemo.databinding.RepositorySeconItemLayoutBinding
import ge.example.githubapidemo.extensions.getFormattedNumber
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem


typealias OnClickRepoItem = (owner: String, repo: String) -> Unit
typealias OnDeleteClickRepoItem = (id: Int) -> Unit

class FavRepoAdapter :
    ListAdapter<RepositoryItem, FavRepoAdapter.CountriesViewHolder>(CountriesComparator()) {

    var repoItemOnClick: OnClickRepoItem? = null
    var deleteRepoItemOnClick: OnDeleteClickRepoItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountriesViewHolder(
            RepositorySeconItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val currentCountries = getItem(position)
        holder.bind(currentCountries)
    }

    inner class CountriesViewHolder(private val binding: RepositorySeconItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(repo: RepositoryItem) {
            binding.apply {
                titleTv.text = repo.fullName
                repoDescTv.text = repo.description
                languageTv.text = repo.language
                startTv.text = repo.stargazersCount?.getFormattedNumber()
                forksTv.text = repo.forksCount?.getFormattedNumber()
                deleteRepoImage.setOnClickListener {
                    deleteRepoItemOnClick?.invoke(repo.id!!)
                }
            }

            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val model = getItem(absoluteAdapterPosition)
            model?.let { repo ->
                repoItemOnClick?.invoke(
                    repo.owner?.ownerLogin!!,
                    repo.name!!
                )
            }
        }
    }


    class CountriesComparator : DiffUtil.ItemCallback<RepositoryItem>() {
        override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: RepositoryItem,
            newItem: RepositoryItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }


}