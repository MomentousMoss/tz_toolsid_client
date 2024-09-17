package com.momentousmoss.tz_toolsid_client.api

import android.net.Uri
import android.util.Xml
import com.google.gson.JsonObject
import com.google.gson.JsonParser.parseReader
import com.momentousmoss.tz_toolsid_client.AUTHORITY
import com.momentousmoss.tz_toolsid_client.PATH_API
import com.momentousmoss.tz_toolsid_client.SCHEME
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStreamReader

open class MainApiImplementation {

    fun baseUrl(): Uri.Builder {
        val urlBuilder = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(PATH_API)
        return urlBuilder
    }

    fun parseResponse(client: OkHttpClient, request: Request): JsonObject {
        val response = client.newCall(request).execute()
        val parsedResponse = parseReader(
            InputStreamReader(response.body!!.source().inputStream(), Xml.Encoding.UTF_8.name)
        ) as JsonObject
        return parsedResponse
    }

}