package com.spielberg.commonext

import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

fun String.getClassInvokeMethodExt(methodName: String, parameterTypes: Array<Class<*>?>): Method? {
    try {
        return Class.forName(this).getMethod(methodName, *parameterTypes)
    } catch (e: SecurityException) {
        e.printStackTrace()
    } catch (e: java.lang.IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    }
    return null
}

fun String.getClassInvokeMethodExt(methodName: String): Method? {
    try {
        return Class.forName(this).getMethod(methodName)
    } catch (e: SecurityException) {
        e.printStackTrace()
    } catch (e: java.lang.IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    }
    return null
}

fun String.getClassInvokeDeclaredFieldExt(filedName: String): Field? {
    try {
        return Class.forName(this).getDeclaredField(filedName)
    } catch (e: SecurityException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: java.lang.IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    }
    return null
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 2/1/21 7:01 PM
 *  @describe 通过类反射调用调用类的静态方法
 */
fun invokeStaticMethodExt(
    className: String?,
    methodMame: String,
    parameterTypes: Array<Class<*>?>,
    pareValues: Array<Any?>
): Any? {
    return className?.getClassInvokeMethodExt(methodMame, parameterTypes)?.invoke(null, pareValues)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 2/1/21 7:02 PM
 *  @describe 通过类反射调用调用类的实例方法
 */
fun invokeMethodExt(
    className: String,
    methodMame: String,
    obj: Any?,
    parameterTypes: Array<Class<*>?>,
    pareValues: Array<Any?>
): Any? {
    return className.getClassInvokeMethodExt(methodMame, parameterTypes)?.invoke(obj, pareValues)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 2/1/21 7:02 PM
 *  @describe 通过类反射调用获取类的实例成员变量
 */
fun getFieldObjectExt(className: String, obj: Any?, filedName: String): Any? {
    val field = className.getClassInvokeDeclaredFieldExt(filedName)
    field?.isAccessible = true
    return if (field != null) field[obj] else null
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 2/1/21 7:02 PM
 *  @describe 通过类反射调用获取类的静态成员变量
 */
fun getStaticFieldObjectExt(className: String, filedName: String): Any? {
    val field = className.getClassInvokeDeclaredFieldExt(filedName)
    field?.isAccessible = true
    return if (field != null) field[null] else null
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 2/1/21 7:02 PM
 *  @describe 通过类反射调用设置类的静态成员变量的值
 */
fun setStaticObjectExt(className: String, filedName: String, filedValue: Any?) {
    val field = className.getClassInvokeDeclaredFieldExt(filedName)
    field?.isAccessible = true
    field?.set(null, filedValue)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 2/1/21 7:02 PM
 *  @describe 通过类反射调用设置类的实例成员变量的值
 */
fun setFieldObjectExt(className: String, filedName: String, obj: Any?, filedValue: Any?) {
    val field = className.getClassInvokeDeclaredFieldExt(filedName)
    field?.isAccessible = true
    field?.set(obj, filedValue)
}