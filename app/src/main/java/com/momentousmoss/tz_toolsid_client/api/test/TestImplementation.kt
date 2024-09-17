package com.momentousmoss.tz_toolsid_client.api.test

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.momentousmoss.tz_toolsid_client.api.JsonService
import com.momentousmoss.tz_toolsid_client.api.MainApiImplementation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class TestImplementation : MainApiImplementation(), TestInterface {

    companion object {
        private const val PATH_TEST = "test"
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_AUTHORIZATION_BODY_PREFIX = "Bearer "
    }

    private fun testRequestUrl(): URL {
        val urlBuilder = baseUrl()
            .appendPath(PATH_TEST)
        return URL(urlBuilder.build().toString())
    }

    private suspend fun testRequestBaseResponse(bearerToken : String): JsonObject? {
        val testUrl = testRequestUrl()
        val client = OkHttpClient()
        return try {
            withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .addHeader(HEADER_AUTHORIZATION, "$HEADER_AUTHORIZATION_BODY_PREFIX$bearerToken")
                    .url(testUrl)
                    .build()
                parseResponse(client, request)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun testRequestDataResponse(bearerToken : String): JsonService.TestData? {
        return getJson(testRequestBaseResponse(bearerToken))?.data
    }

    override suspend fun testRequestBlockResponse(bearerToken : String): Boolean? {
        return getJson(testRequestBaseResponse(bearerToken))?.data?.is_blocked
    }

    private fun getJson(jsonObject: JsonObject?): JsonService.Test? {
        return try {
             Gson().fromJson(jsonObject, JsonService.Test::class.java)
        } catch (e: Exception) {
            null
        }
    }

}