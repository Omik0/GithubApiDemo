package ge.example.githubapidemo.feature_github_repositories.domain.repository

import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun searchRepository(
        query: String,
        perPage: Int,
        page: Int
    ): Resource<GithubResponse>

    suspend fun getRepository(
        owner: String,
        repo: String,
    ): Resource<RepositoryItem>

    fun getRepos(): Flow<List<RepositoryItem>>

    suspend fun getRepoById(id: Int): RepositoryItem?

    suspend fun insertRepo(repo: RepositoryItem)

    suspend fun deleteRepo(id: Int)
}