package com.momentousmoss.tz_toolsid_client.api.login

import android.net.Uri
import android.util.Xml
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser.parseReader
import com.momentousmoss.tz_toolsid_client.api.JsonService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStreamReader
import java.net.URL

class LoginImplementation : LoginInterface {

    companion object {
        private const val SCHEME = "http"
        private const val AUTHORITY = "90.156.204.236"
        private const val PATH_API = "api"
        private const val PATH_LOGIN = "login"
        private const val PARAMETER_EMAIL = "email"
        private const val PARAMETER_PASSWORD = "password"
    }

    override suspend fun login(login: String, password: String): JsonService.LoginData? {
        val loginUrl = loginUrl(login, password)
        val client = OkHttpClient()
        return try {
            withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .url(loginUrl)
                    .post("".toRequestBody(null))
                    .build()
                val response = client.newCall(request).execute()
                val parsedResponse = parseReader(
                    InputStreamReader(response.body!!.source().inputStream(), Xml.Encoding.UTF_8.name)
                ) as JsonObject
                Gson().fromJson(parsedResponse, JsonService.Login::class.java).data
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun loginUrl(login: String, password: String): URL {
        val urlBuilder = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(PATH_API)
            .appendPath(PATH_LOGIN)
            .appendQueryParameter(PARAMETER_EMAIL, login)
            .appendQueryParameter(PARAMETER_PASSWORD, password)
        return URL(urlBuilder.build().toString())
    }

}