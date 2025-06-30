package com.colman.kmp2025.data.networking

import io.ktor.http.HttpMethod

interface Endpoint {

    val path: String

    val method: HttpMethod

    val queryParameters: List<QueryParam>?
        get() = null

    val headers: List<Header>?
        get() = null

    val body: Any?
        get() = null

    val isAuthenticated: Boolean
        get() = false
}