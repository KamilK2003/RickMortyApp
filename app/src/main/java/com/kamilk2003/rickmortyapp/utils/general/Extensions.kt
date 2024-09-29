package com.kamilk2003.rickmortyapp.utils.general

fun <T> Collection<T>.notContains(element: @UnsafeVariance T) = this.contains(element).not()