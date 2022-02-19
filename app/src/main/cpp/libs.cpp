#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_ge_example_githubapidemo_Keys_githubToken(JNIEnv *env, jobject thiz) {
    std::string github_token = "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";
    return env->NewStringUTF(github_token.c_str());
}