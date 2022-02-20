package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.use_cases.RepositoryUseCases
import ge.example.githubapidemo.feature_github_repositories.domain.use_cases.SearchRepositoryUseCase
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter.ReposDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryUseCases: RepositoryUseCases
) :
    ViewModel() {

    private val _state =
        MutableStateFlow<PagingData<RepositoryItem>>(PagingData.empty())
    val state get() = _state.asStateFlow()

    init {
        getUserResponse("Github")
    }

    fun getUserResponse(query: String) =
        viewModelScope.launch {
            delay(500)
            Pager(
                config = PagingConfig(
                    pageSize = 1,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    ReposDataSource(
                        query,
                        repositoryUseCases.searchRepositoryUseCase
                    )
                }
            ).flow.cachedIn(viewModelScope).collectLatest {
                _state.value = it
            }
        }
}