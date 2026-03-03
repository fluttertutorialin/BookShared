package com.multiplatform.book

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform