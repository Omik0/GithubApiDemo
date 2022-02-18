#include <jni.h>
#include <string>
extern "C"
JNIEXPORT jstring JNICALL
Java_ge_example_githubapidemo_Keys_githubToken(JNIEnv *env, jobject thiz) {
    std::string github_token = "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";
    return env->NewStringUTF(github_token.c_str());
}