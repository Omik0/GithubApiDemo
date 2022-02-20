package ge.example.githubapidemo.feature_github_repositories.data.repository

import ge.example.githubapidemo.feature_github_repositories.data.local_data_source.GithubDao
import ge.example.githubapidemo.feature_github_repositories.data.remote_data_source.GithubService
import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.repository.GithubRepository
import ge.example.githubapidemo.feature_github_repositories.domain.utils.ConnectivityListener
import ge.example.githubapidemo.feature_github_repositories.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GithubRepositoryImpl(
    private val githubApi: GithubService,
    private val githubDao: GithubDao,
    private val connectionListener: ConnectivityListener
) : GithubRepository {
    override suspend fun searchRepository(
        query: String,
        perPage: Int,
        page: Int
    ): Resource<GithubResponse> =
        handleResponse { githubApi.searchRepository(query, perPage, page) }

    override suspend fun getRepository(
        owner: String,
        repo: String
    ): Resource<RepositoryItem> =
        handleResponse { githubApi.getRepository(owner, repo) }

    override fun getRepos(): Flow<List<RepositoryItem>> {
        return githubDao.getRepos()
    }

    override suspend fun getRepoById(id: Int): RepositoryItem? {
        return githubDao.getRepoById(id)
    }

    override suspend fun insertRepo(repo: RepositoryItem) {
        return githubDao.insertRepo(repo)
    }

    override suspend fun deleteRepo(id: Int) {
        return githubDao.deleteRepoById(id)
    }


    private suspend fun <M> handleResponse(
        request: suspend () -> Response<M>
    ): Resource<M> {
        return try {
            if (connectionListener.value == true) {
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
            } else
                Resource.Error("No internet connection!")

        } catch (e: Throwable) {
            Resource.Error(e.message.toString(), null)
        }
    }
}