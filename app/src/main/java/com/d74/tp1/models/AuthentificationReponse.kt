package com.d74.tp1.models

data class AuthentificationReponse(
    val access_token: String,
    val emailaddress: String,
    val expires_in: Int,
    val roles: String,
    val token_type: String,
    val userName: String
)