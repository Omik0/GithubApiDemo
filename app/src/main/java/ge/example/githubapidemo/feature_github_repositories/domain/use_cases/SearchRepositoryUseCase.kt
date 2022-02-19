package ge.example.githubapidemo.feature_github_repositories.domain.use_cases

import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.repository.GithubRepository
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class SearchRepositoryUseCase(private val githubRepository: GithubRepository) {
    suspend operator fun invoke(
        query: String,
        perPage: Int = 20,
        page: Int = 1
    ): Resource<GithubResponse> {
        return githubRepository.searchRepository(query, perPage, page)
    }

}