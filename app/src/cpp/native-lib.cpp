#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_abdelmageed_mazadytask_data_di_KtorModule_getBaseUrl(JNIEnv *env, jobject thiz) {
    std::string baseURL = "https://api.themoviedb.org/";
    return env->NewStringUTF(baseURL.c_str());
}