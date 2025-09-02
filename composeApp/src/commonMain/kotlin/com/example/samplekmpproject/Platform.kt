package com.example.samplekmpproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform