package ge.example.githubapidemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.example.githubapidemo.feature_github_repositories.data.remote.GithubService
import ge.example.githubapidemo.feature_github_repositories.data.repository.GithubRepositoryImpl
import ge.example.githubapidemo.feature_github_repositories.domain.repository.GithubRepository
import ge.example.githubapidemo.feature_github_repositories.domain.use_cases.SearchRepositoryUseCase
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit.Builder): GithubService {
        return retrofit.build()
            .create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubRepository(
        githubService: GithubService,
    ): GithubRepository {
        return GithubRepositoryImpl(githubService)
    }

    @Provides
    @Singleton
    fun provideGithubUseCases(repository: GithubRepository): SearchRepositoryUseCase {
        return SearchRepositoryUseCase(repository)
    }

}