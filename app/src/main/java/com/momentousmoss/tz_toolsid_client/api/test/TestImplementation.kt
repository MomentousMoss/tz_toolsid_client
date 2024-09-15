package com.momentousmoss.tz_toolsid_client.api.test

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
import java.io.InputStreamReader
import java.net.URL

class TestImplementation : TestInterface {

    companion object {
        private const val SCHEME = "http"
        private const val AUTHORITY = "90.156.204.236"
        private const val PATH_API = "api"
        private const val PATH_TEST = "test"
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_AUTHORIZATION_BODY_PREFIX = "Bearer "
    }

    override suspend fun testRequest(bearerToken : String): JsonService.TestData? {
        val testUrl = testUrl()
        val client = OkHttpClient()
        return try {
            withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .addHeader(HEADER_AUTHORIZATION, "$HEADER_AUTHORIZATION_BODY_PREFIX$bearerToken")
                    .url(testUrl)
                    .build()
                val response = client.newCall(request).execute()
                val parsedResponse = parseReader(
                    InputStreamReader(response.body!!.source().inputStream(), Xml.Encoding.UTF_8.name)
                ) as JsonObject
                Gson().fromJson(parsedResponse, JsonService.Test::class.java).data
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun testUrl(): URL {
        val urlBuilder = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(PATH_API)
            .appendPath(PATH_TEST)
        return URL(urlBuilder.build().toString())
    }

}