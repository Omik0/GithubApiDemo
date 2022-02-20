package ge.example.githubapidemo.feature_github_repositories.domain.model


import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.lang.Exception

data class GithubResponse(
    @Json(name = "items")
    val repositoryItems: List<RepositoryItem>?,
    @Json(name = "total_count")
    val totalCount: Int?
) {
    @Entity(tableName = "repos")
    @Parcelize
    data class RepositoryItem(
        @Json(name = "id")
        @PrimaryKey val id: Int?,
        @Json(name = "created_at")
        val createdAt: String?,
        @Json(name = "topics")
        val topics: List<String>?,
        @Json(name = "default_branch")
        val defaultBranch: String?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "fork")
        val fork: Boolean?,
        @Json(name = "forks")
        val forks: Int?,
        @Json(name = "forks_count")
        val forksCount: Int?,
        @Json(name = "full_name")
        val fullName: String?,
        @Json(name = "has_pages")
        val hasPages: Boolean?,
        @Json(name = "html_url")
        val htmlUrl: String?,
        @Json(name = "language")
        val language: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "node_id")
        val nodeId: String?,
        @Json(name = "open_issues_count")
        val openIssuesCount: Int?,
        @Json(name = "owner")
        @Embedded val owner: Owner?,
        @Json(name = "pushed_at")
        val pushedAt: String?,
        @Json(name = "releases_url")
        val releasesUrl: String?,
        @Json(name = "stargazers_count")
        val stargazersCount: Int?,
        @Json(name = "stargazers_url")
        val stargazersUrl: String?,
        @Json(name = "statuses_url")
        val statusesUrl: String?,
        @Json(name = "updated_at")
        val updatedAt: String?,
        @Json(name = "url")
        val url: String?,
        @Json(name = "watchers")
        val watchers: Int?,
        @Json(name = "watchers_count")
        val watchersCount: Int?
    ) : Parcelable {
        @Parcelize
        data class Owner(
            @Json(name = "avatar_url")
            val avatarUrl: String?,
            @Json(name = "html_url")
            val ownerHtmlUrl: String?,
            @Json(name = "id")
            val id: Int?,
            @Json(name = "login")
            val login: String?,
            @Json(name = "node_id")
            val nodeId: String?,
            @Json(name = "url")
            val url: String?
        ) : Parcelable
    }
}

class InvalidRepoException(message: String?) : Exception(message)
