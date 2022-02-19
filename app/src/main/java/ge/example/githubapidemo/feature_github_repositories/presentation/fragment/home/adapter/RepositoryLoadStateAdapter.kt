package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ge.example.githubapidemo.databinding.LoadStateLayoutBinding

class RepositoryLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RepositoryLoadStateAdapter.LoadStateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            LoadStateLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: LoadStateLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun onBind(loadState: LoadState) {
            binding.apply {
                loadingProgress.isVisible = loadState is LoadState.Loading
                retryBtn.isVisible = loadState !is LoadState.Loading
                errorTv.isVisible = loadState !is LoadState.Loading
            }
        }
    }


}