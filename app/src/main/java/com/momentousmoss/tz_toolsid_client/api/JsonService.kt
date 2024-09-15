package com.momentousmoss.tz_toolsid_client.api

open class JsonService {

    data class LoginData (
        var token : String? = null,
        var name : String? = null
    )

    data class Login (
        var success : String? = null,
        var data : LoginData? = null,
        var message : String? = null
    )

    data class TestData (
        var something : String? = null
    )

    data class Test (
        var success : String? = null,
        var data : TestData? = null,
        var message : String? = null
    )

}