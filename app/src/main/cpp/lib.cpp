#include <jni.h>
#include <string>
extern "C"
JNIEXPORT jstring JNICALL
Java_ge_example_githubapidemo_Keys_githubToken(JNIEnv *env, jobject thiz) {
    std::string github_token = "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";
    return env->NewStringUTF(github_token.c_str());
}