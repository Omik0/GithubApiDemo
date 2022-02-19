package ge.example.githubapidemo.feature_github_repositories.domain.model


import com.squareup.moshi.Json

data class GithubResponse(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean?,
    @Json(name = "items")
    val repositoryItems: List<RepositoryItem>?,
    @Json(name = "total_count")
    val totalCount: Int?
)