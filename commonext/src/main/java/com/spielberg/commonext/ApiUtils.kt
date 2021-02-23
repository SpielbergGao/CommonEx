package com.spielberg.commonext

import android.util.Log
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ApiUtils private constructor() {

    private val mApiMap: MutableMap<Class<*>, BaseApi?> = ConcurrentHashMap()
    private val mInjectApiImplMap: MutableMap<Class<*>, Class<*>> = HashMap()

    /**
     * It'll be injected the implClasses who have [ApiUtils.Api] annotation
     * by function of [ApiUtils.registerImpl] when execute transform task.
     */
    private fun init() { /*inject*/
    }

    private fun registerImpl(implClass: Class<*>) {
        mInjectApiImplMap[implClass.superclass] = implClass
    }

    override fun toString(): String {
        return "ApiUtils: $mInjectApiImplMap"
    }

    private fun <Result> getApiInner(apiClass: Class<*>): Result? {
        var api = mApiMap[apiClass]
        if (api != null) {
            return api as Result
        }
        synchronized(apiClass) {
            api = mApiMap[apiClass]
            if (api != null) {
                return api as Result
            }
            val implClass = mInjectApiImplMap[apiClass]
            return if (implClass != null) {
                try {
                    api = implClass.newInstance() as BaseApi
                    mApiMap[apiClass] = api
                    api as Result?
                } catch (ignore: java.lang.Exception) {
                    Log.e(
                        TAG,
                        "The <$implClass> has no parameterless constructor."
                    )
                    null
                }
            } else {
                Log.e(TAG, "The <$apiClass> doesn't implement.")
                null
            }
        }
    }

    @Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
    @Retention(RetentionPolicy.CLASS)
    annotation class Api(val isMock: Boolean = false)

    class BaseApi
    companion object {

        private const val TAG = "ApiUtils"

        private object LazyHolder {
            val INSTANCE = ApiUtils()
        }

        private fun getInstance(): ApiUtils {
            return LazyHolder.INSTANCE
        }

        /**
         * Get api.
         *
         * @param apiClass The class of api.
         * @param <T>      The type.
         * @return the api
        </T> */
        fun <T : BaseApi?> getApi(apiClass: Class<T>): T? {
            return getInstance().getApiInner(apiClass)
        }

        fun register(implClass: Class<out BaseApi>) {
            getInstance().registerImpl(implClass)
        }

        fun toString_(): String {
            return getInstance().toString()
        }
    }

    init {
        init()
    }
}