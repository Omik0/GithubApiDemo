package ge.example.githubapidemo.feature_github_repositories.domain.use_cases

data class RepositoryUseCases(
    val searchRepositoryUseCase: SearchRepositoryUseCase,
    val getRepositoryUseCase: GetRepositoryUseCase,
    val getRepositoryFromRoomUseCase: GetRepositoryFromRoomUseCase,
    val getRepoByIdUseCase: GetRepoByIdUseCase,
    val addRepoUseCase: AddRepoUseCase,
    val deleteRepoUseCase: DeleteRepoUseCase
)