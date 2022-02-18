package ge.example.githubapidemo

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun githubToken(): String
}
