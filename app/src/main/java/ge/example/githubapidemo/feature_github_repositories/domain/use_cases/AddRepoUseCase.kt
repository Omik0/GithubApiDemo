package ge.example.githubapidemo.feature_github_repositories.domain.use_cases

import ge.example.githubapidemo.feature_github_repositories.domain.model.RepositoryItem
import ge.example.githubapidemo.feature_github_repositories.domain.repository.GithubRepository

class AddRepoUseCase(private val githubRepository: GithubRepository) {

//    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(repo: RepositoryItem) {
//        if (note.title.isNotBlank()) {
//            throw InvalidNoteException("The title of this note can't be empty!")
//        }
//        if (note.content.isNotBlank()) {
//            throw InvalidNoteException("The content of this note can't be empty!")
//        }
        githubRepository.insertRepo(repo)
    }

}