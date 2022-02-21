package ge.example.githubapidemo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ge.example.githubapidemo.feature_github_repositories.data.local_data_source.GithubDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideGithubDatabase(@ApplicationContext app: Context): GithubDatabase {
        return Room.databaseBuilder(
            app,
            GithubDatabase::class.java,
            GithubDatabase.DATABASE_NAME
        ).build()
    }

}