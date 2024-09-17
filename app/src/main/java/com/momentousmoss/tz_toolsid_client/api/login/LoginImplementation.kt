package com.momentousmoss.tz_toolsid_client.api.login

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.momentousmoss.tz_toolsid_client.api.JsonService
import com.momentousmoss.tz_toolsid_client.api.MainApiImplementation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URL

class LoginImplementation : MainApiImplementation(), LoginInterface {

    companion object {
        private const val PATH_LOGIN = "login"
        private const val PARAMETER_EMAIL = "email"
        private const val PARAMETER_PASSWORD = "password"
    }

    private fun loginUrl(login: String, password: String): URL {
        val urlBuilder = baseUrl()
            .appendPath(PATH_LOGIN)
            .appendQueryParameter(PARAMETER_EMAIL, login)
            .appendQueryParameter(PARAMETER_PASSWORD, password)
        return URL(urlBuilder.build().toString())
    }

    private suspend fun loginRequestBaseResponse(login: String, password: String): JsonObject? {
        val loginUrl = loginUrl(login, password)
        val client = OkHttpClient()
        return try {
            withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .url(loginUrl)
                    .post("".toRequestBody(null))
                    .build()
                parseResponse(client, request)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun loginRequestDataResponse(login: String, password: String): JsonService.LoginData? {
        return getJson(loginRequestBaseResponse(login, password))?.data
    }

    private fun getJson(jsonObject: JsonObject?): JsonService.Login? {
        return Gson().fromJson(jsonObject, JsonService.Login::class.java)
    }

}