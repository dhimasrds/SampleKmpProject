package com.example.samplekmpproject.core.network

sealed class NetworkError(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    class Client(val code: Int, val body: String?) : NetworkError("Client error $code: $body")
    class Server(val code: Int, val body: String?) : NetworkError("Server error $code: $body")
    object Timeout : NetworkError("Request timeout")
    object Unreachable : NetworkError("Network unreachable")
    class Unknown(cause: Throwable) : NetworkError(cause.message, cause)
}
