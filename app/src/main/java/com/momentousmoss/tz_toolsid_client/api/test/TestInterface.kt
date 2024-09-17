package com.momentousmoss.tz_toolsid_client.api.test

import com.momentousmoss.tz_toolsid_client.api.JsonService

interface TestInterface {

    suspend fun testRequestDataResponse(bearerToken : String): JsonService.TestData?

    suspend fun testRequestBlockResponse(bearerToken : String): Boolean?

}