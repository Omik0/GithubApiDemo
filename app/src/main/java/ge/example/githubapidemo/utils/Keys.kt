package ge.example.githubapidemo.utils

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun githubToken(): String
}
