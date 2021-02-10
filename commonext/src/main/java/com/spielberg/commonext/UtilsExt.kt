package com.spielberg.commonext

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe 如果 version1 > version2 返回 1，
 *  如果 version1 < version2 返回 -1，
 *  除此之外返回 0。
 */
fun compareVersionExt(version1: String, version2: String): Int {
    val num1 = version1.split("\\.".toRegex()).toTypedArray()
    val num2 = version2.split("\\.".toRegex()).toTypedArray()
    val n1 = num1.size
    val n2 = num2.size
    var i1: Int
    var i2: Int
    val length = n1.coerceAtLeast(n2)
    for (i in 0 until length) {
        i1 = if (i < n1) num1[i].toInt() else 0
        i2 = if (i < n2) num2[i].toInt() else 0
        if (i1 != i2) {
            return if (i1 > i2) 1 else -1
        }
    }
    return 0
}