package ge.example.githubapidemo.feature_github_repositories.domain.use_cases

import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.repository.GithubRepository
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRepositoryUseCase(private val githubRepository: GithubRepository) {
    suspend operator fun invoke(
        owner: String,
        repo: String,
    ): Flow<Resource<RepositoryItem>> = flow {
        emit(githubRepository.getRepository(owner, repo))
    }
}