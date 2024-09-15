package com.momentousmoss.tz_toolsid_client.api.test

import com.momentousmoss.tz_toolsid_client.api.JsonService

interface TestInterface {

    suspend fun testRequest(bearerToken : String): JsonService.TestData?
}