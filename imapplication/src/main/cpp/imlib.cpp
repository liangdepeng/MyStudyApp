#include <jni.h>
#include <string>

//
// Created by CTWL on 2024/2/23.
//


extern "C"
JNIEXPORT jstring JNICALL
Java_com_dpim_application_http_Utils_getKey(JNIEnv *env, jclass clazz) {
    std::string str = "8luwapkv8huwl";
    return env->NewStringUTF(str.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_dpim_application_http_Utils_getAppSecret(JNIEnv *env, jclass clazz) {
    std::string str = "i3sRC77Ab0yYC";
    return env->NewStringUTF(str.c_str());
}