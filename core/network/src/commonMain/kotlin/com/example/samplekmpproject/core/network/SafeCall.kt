package com.example.samplekmpproject.core.network

import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CancellationException

suspend inline fun <reified T> safeCall(block: () -> T): T {
    try {
        return block()
    } catch (e: ResponseException) {
        val code = e.response.status.value
        val bodyText = runCatching { e.response.bodyAsText() }.getOrNull()
        if (code in 400..499) throw NetworkError.Client(code, bodyText) else throw NetworkError.Server(code, bodyText)
    } catch (e: HttpRequestTimeoutException) {
        throw NetworkError.Timeout
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        throw NetworkError.Unknown(e)
    }
}
