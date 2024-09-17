package com.momentousmoss.tz_toolsid_client.api.login

import com.momentousmoss.tz_toolsid_client.api.JsonService

interface LoginInterface {

    suspend fun loginRequestDataResponse(login: String, password: String): JsonService.LoginData?

}