#include <jni.h>
#include <string>


extern "C" JNIEXPORT jstring JNICALL
Java_com_gs_nasaapod_ui_main_MainRepository_getAPIKey(JNIEnv *env, jobject obj) {
    return env->NewStringUTF("Fz8mVpshcRt7S298ySrk84QgF47dbSGh2CmvlgvT");
}