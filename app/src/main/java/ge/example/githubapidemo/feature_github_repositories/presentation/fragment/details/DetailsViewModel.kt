package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.use_cases.RepositoryUseCases
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repositoryUseCases: RepositoryUseCases
) : ViewModel() {

    private val _repoState =
        MutableStateFlow<Resource<GithubResponse.RepositoryItem>>(Resource.Loading())
    val repoState get() = _repoState.asStateFlow()

    fun getRepo(owner: String, repo: String) {
        viewModelScope.launch {
            repositoryUseCases.getRepositoryUseCase(owner, repo).collectLatest {
                _repoState.value = it
            }
        }
    }

}