package ge.example.githubapidemo.feature_github_repositories.domain.use_cases

import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.repository.GithubRepository
import ge.example.githubapidemo.feature_github_repositories.domain.utils.OrderType
import ge.example.githubapidemo.feature_github_repositories.domain.utils.RepoOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRepositoryFromRoomUseCase(private val githubRepository: GithubRepository) {
    operator fun invoke(
        repoOrder: RepoOrder = RepoOrder.Name(OrderType.Descending)
    ): Flow<List<RepositoryItem>> {
        return githubRepository.getRepos().map { repo ->
            when (repoOrder.orderType) {
                is OrderType.Ascending -> {
                    when (repoOrder) {
                        is RepoOrder.Name -> repo.sortedBy { it.name?.lowercase() }
                        is RepoOrder.Fork -> repo.sortedBy { it.forks }
                        is RepoOrder.Issue -> repo.sortedBy { it.openIssuesCount }
                    }
                }
                is OrderType.Descending -> {
                    when (repoOrder) {
                        is RepoOrder.Name -> repo.sortedByDescending { it.name?.lowercase() }
                        is RepoOrder.Fork -> repo.sortedByDescending { it.forks }
                        is RepoOrder.Issue -> repo.sortedByDescending { it.openIssuesCount }
                    }
                }
            }
        }
    }

}