package ge.example.githubapidemo.feature_github_repositories.data.local_data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem

@Database(
    entities = [RepositoryItem::class],
    version = 1
)
abstract class GithubDatabase : RoomDatabase() {
    abstract val githubDao: GithubDao

    companion object {
        const val DATABASE_NAME = "github_db"
    }
}