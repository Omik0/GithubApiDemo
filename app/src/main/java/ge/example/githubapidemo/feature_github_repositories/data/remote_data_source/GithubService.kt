package ge.example.githubapidemo.feature_github_repositories.data.remote_data_source

import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    @Headers("accept: application/vnd.github.v3+json")
    suspend fun searchRepository(
        @Query("q") query: String = "Github",
        @Query("per_page") perPage: Int? = 30,
        @Query("page") page: Int? = 1
    ): Response<GithubResponse>

    @GET("repos/{owner}/{repo}")
    @Headers("accept: application/vnd.github.v3+json")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Response<GithubResponse.RepositoryItem>
}