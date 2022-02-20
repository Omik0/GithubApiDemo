package ge.example.githubapidemo.feature_github_repositories.presentation.fragment.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.use_cases.RepositoryUseCases
import ge.example.githubapidemo.feature_github_repositories.domain.utils.OrderType
import ge.example.githubapidemo.feature_github_repositories.domain.utils.RepoOrder
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repositoryUseCases: RepositoryUseCases
) : ViewModel() {

    private val _state =
        MutableStateFlow<List<RepositoryItem>>(emptyList())
    val state get() = _state.asStateFlow()

    private var job: Job? = null

    init {
        getReposFromRoom()
    }

    private fun getReposFromRoom() {
        job?.cancel()
        job = viewModelScope.launch {
            repositoryUseCases.getRepositoryFromRoomUseCase()
                .collectLatest {
                    _state.value = it
                }
        }
    }

    fun deleteRepoFromRoom(id: Int) {
        viewModelScope.launch {
            repositoryUseCases.deleteRepoUseCase(id)
        }
    }
}