package ge.example.githubapidemo.feature_github_repositories.domain.utils

sealed class RepoOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): RepoOrder(orderType)
    class Fork(orderType: OrderType): RepoOrder(orderType)
    class Issue(orderType: OrderType): RepoOrder(orderType)
}