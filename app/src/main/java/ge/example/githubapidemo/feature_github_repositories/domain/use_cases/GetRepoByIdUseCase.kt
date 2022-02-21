package ge.example.githubapidemo.feature_github_repositories.domain.use_cases

import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.repository.GithubRepository

class GetRepoByIdUseCase(
    private val githubRepository: GithubRepository
) {
    suspend operator fun invoke(id: Int): RepositoryItem? {
        return githubRepository.getRepoById(id)
    }
}