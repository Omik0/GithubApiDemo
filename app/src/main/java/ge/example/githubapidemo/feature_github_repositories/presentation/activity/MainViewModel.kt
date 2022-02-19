package ge.example.githubapidemo.feature_github_repositories.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.use_cases.SearchRepositoryUseCase
import ge.example.githubapidemo.feature_github_repositories.presentation.fragment.home.adapter.ReposDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepositoryUseCase: SearchRepositoryUseCase
) :
    ViewModel() {

    private val _state =
        MutableStateFlow<PagingData<GithubResponse.RepositoryItem>>(PagingData.empty())
    val state get() = _state.asStateFlow()

    init {
        getUserResponse("Github")
    }

    fun getUserResponse(query: String) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 1,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { ReposDataSource(query, searchRepositoryUseCase) }
            ).flow.cachedIn(viewModelScope).collectLatest {
                _state.value = it
            }
        }
    }
}