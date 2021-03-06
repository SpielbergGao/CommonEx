package com.spielberg.commonext

import java.nio.charset.Charset

fun Charset?.toCharset(): Charset? {
    return this ?: Charset.defaultCharset()
}

fun String?.toCharset(): Charset? {
    return if (this.isEmptyOrBlankExt()) Charset.defaultCharset() else Charset.forName(this)
}

val ISO_8859_1: Charset = Charset.forName("ISO-8859-1")
val US_ASCII: Charset = Charset.forName("US-ASCII")
val UTF_16: Charset = Charset.forName("UTF-16")
val UTF_16BE: Charset = Charset.forName("UTF-16BE")
val UTF_16LE: Charset = Charset.forName("UTF-16LE")
val UTF_8: Charset = Charset.forName("UTF-8")