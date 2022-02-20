package ge.example.githubapidemo.feature_github_repositories.data.local_data_source

import androidx.room.*
import ge.example.githubapidemo.feature_github_repositories.domain.model.GithubResponse
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {

    @Query("SELECT * FROM repos")
    fun getRepos(): Flow<List<RepositoryItem>>

    @Query("SELECT * FROM repos WHERE id = :id")
    suspend fun getRepoById(id: Int): RepositoryItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: RepositoryItem)

    @Query("DELETE FROM repos WHERE id = :id")
    suspend fun deleteRepoById(id: Int)

}