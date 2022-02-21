package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ge.example.githubapidemo.feature_github_repositories.domain.model.InvalidRepoException
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.use_cases.SearchRepositoryUseCase
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource

const val USER_STARTING_PAGE_INDEX = 1

class ReposDataSource(
    private val query: String,
    private val searchRepositoryUseCase: SearchRepositoryUseCase
) :
    PagingSource<Int, RepositoryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryItem> {
        val position = params.key ?: USER_STARTING_PAGE_INDEX
        return when (val repos = searchRepositoryUseCase(query = query, page = position)) {
            is Resource.Success -> {
                repos.data?.repositoryItems.let {
                    LoadResult.Page(
                        data = it ?: emptyList(),
                        prevKey = if (position == USER_STARTING_PAGE_INDEX) null else position - 1,
                        nextKey = if (it == null || it.isEmpty()) null else position + 1
                    )
                }
            }
            is Resource.Error -> {
                LoadResult.Error(InvalidRepoException(repos.message))
            }
            else -> {
                LoadResult.Error(Throwable())
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}