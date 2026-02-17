package hc.b4sh0xf.talk

import com.google.gson.annotations.SerializedName

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val message: String, val token: String?)
data class LastLoginResponse(val username: String, val timestamp: String)