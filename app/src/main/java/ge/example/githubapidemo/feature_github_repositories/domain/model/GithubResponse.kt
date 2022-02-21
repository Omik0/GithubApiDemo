package ge.example.githubapidemo.feature_github_repositories.domain.model


import com.squareup.moshi.Json

data class GithubResponse(
    @Json(name = "items")
    val repositoryItems: List<RepositoryItem>?,
    @Json(name = "total_count")
    val totalCount: Int?
)

class InvalidRepoException(message: String?) : Exception(message)
