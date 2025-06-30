package com.colman.kmp2025.data.networking

import com.colman.kmp2025.data.Error
import com.colman.kmp2025.data.Result

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType

interface Authenticating {
    val isAuthenticated: Boolean
}

interface NetworkClient {
    val client: HttpClient
    val baseUrl: String
    val authenticator: Authenticating
}

class Api<ENDPOINT: Endpoint> (
    val networkClient: NetworkClient
) {

    suspend inline fun <reified RESPONSE : Any, ERROR: Error> request(
        endpoint: ENDPOINT
    ) : Result<RESPONSE, ERROR> {
//        try {
            val response = networkClient.client.request(endpoint.path) {
                method = endpoint.method

                endpoint.body?.let { body ->
                    setBody(body)
                }

                endpoint.queryParameters?.let { params ->
                    {
                        params.forEach { param ->
                            parameter(param.key, param.value)
                        }
                    }
                }

                endpoint.headers?.let { headers ->
                    headers.forEach { header ->
                        accept(ContentType.Application.Json)
                        header(header.key, header.value)
                    }
                }
            }

            return Result.Success(response.body())
//        } catch (throwable: Throwable) {
//            val error = TMDBError(
//                message = throwable.message.toString()
//            ) as? ERROR
//            return Result.Failure(error)
//        }
    }
}