package ge.example.githubapidemo.feature_github_repositories.domain.utils

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
