package ge.example.githubapidemo.feature_github_repositories.data.repository

import ge.example.githubapidemo.feature_github_repositories.data.remote_data_source.GithubService
import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.repository.GithubRepository
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import retrofit2.Response

class GithubRepositoryImpl(private val githubApi: GithubService) : GithubRepository {
    override suspend fun searchRepository(
        query: String,
        perPage: Int,
        page: Int
    ): Resource<GithubResponse> =
        handleResponse { githubApi.searchRepository(query, perPage, page) }


    private suspend fun <M> handleResponse(
        request: suspend () -> Response<M>
    ): Resource<M> {
        return try {
            val result = request.invoke()
            val body = result.body()
            if (result.isSuccessful && body != null) {
                return Resource.Success(body)
            }
//            else if (result.code() == 401) {
//                val jObjError =
//                    org.json.JSONObject(result.errorBody()!!.charStream().readText())
//                Resource.Error(jObjError.getString(ERROR_JSON_NAME))
//            }
            else {
                Resource.Error(result.message())
            }

        } catch (e: Throwable) {
            Resource.Error(e.message.toString(), null)
        }
    }
}