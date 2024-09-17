package com.momentousmoss.tz_toolsid_client.api

import java.util.Date

open class JsonService {

    data class Login (
        var success : String? = null,
        var data : LoginData? = null,
        var message : String? = null
    )

    data class LoginData (
        var token : String? = null,
        var name : String? = null
    )

    data class Test (
        var success : String? = null,
        var data : TestData? = null,
        var message : String? = null
    )

    data class TestData (
        var user : TestDataUser? = null,
        var is_blocked : Boolean? = null,
        var qr : TestDataQR? = null,
        var request : List<Any?>? = null
    )

    data class TestDataUser (
        var id : Int? = null,
        var name : String? = null,
        var email : String? = null,
        var is_confirm : Int? = null,
        var email_verified_at : Date? = null,
        var created_at : Date? = null,
        var updated_at : Date? = null,
        var thema : Int? = null,
        var lang : String? = null
    )

    data class TestDataQR (
        var cipher : String? = null,
        var key : String? = null,
        var payload : List<TestDataQRPayload?>? = null
    )

    data class TestDataQRPayload (
        var block_id : Int? = null,
        var block_name : String? = null,
        var block_ping : String? = null,
        var stage_name : String? = null,
        var stage_address : String? = null
    )

}