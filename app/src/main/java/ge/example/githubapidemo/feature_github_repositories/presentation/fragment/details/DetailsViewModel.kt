package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.details

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.use_cases.RepositoryUseCases
import ge.example.githubapidemo.feature_github_repositories.domain.utils.RepoOrder
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repositoryUseCases: RepositoryUseCases
) : ViewModel() {

    private val _repoState =
        MutableStateFlow<Resource<RepositoryItem>>(Resource.Loading())
    val repoState get() = _repoState.asStateFlow()

    private val _repoExistsState =
        MutableStateFlow(false)
    val repoExistsState get() = _repoExistsState.asStateFlow()


    fun getRepo(owner: String, repo: String) {
        viewModelScope.launch {
            repositoryUseCases.getRepositoryUseCase(owner, repo).collectLatest {
                _repoState.value = it
            }
        }
    }

    fun changeValue() {
        _repoExistsState.value = !_repoExistsState.value
    }

    fun addRepoInDatabase(repo: RepositoryItem) {
        viewModelScope.launch {
            repositoryUseCases.addRepoUseCase(repo)
        }
    }

    fun checkRepoIfExists(repo: RepositoryItem) {
        viewModelScope.launch {
            _repoExistsState.value = repositoryUseCases.getRepoByIdUseCase(repo.id!!) != null
        }
    }

    fun deleteRepoFromRoom(repo: RepositoryItem) {
        viewModelScope.launch {
            repositoryUseCases.deleteRepoUseCase(repo.id!!)
        }
    }

}