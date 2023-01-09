package com.bss.carrent.api.client

import okhttp3.*

class Authentication(
    private val username: String?,
    private val password: String?
) : Authenticator {
    private var _count = 0

    override fun authenticate(route: Route?, response: Response): Request? {
        if (_count++ > 0) {
            return null
        } else {
            if (response.request.header("Authorization") != null || username == null || password == null) {
                return response.request
            }
            val credential = Credentials.basic(username, password)
            return response.request.newBuilder().header("Authorization", credential).build()
        }
        return response.request
    }
}