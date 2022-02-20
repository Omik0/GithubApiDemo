package ge.example.githubapidemo.feature_github_repositories.domain.use_cases

data class RepositoryUseCases(
    val searchRepositoryUseCase: SearchRepositoryUseCase,
    val getRepositoryUseCase: GetRepositoryUseCase
)