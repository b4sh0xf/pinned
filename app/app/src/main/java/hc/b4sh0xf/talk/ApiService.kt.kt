package hc.b4sh0xf.talk

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/auth/register")
    fun register(@Body request: AuthRequest): Call<AuthResponse>

    @POST("api/auth/login")
    fun login(@Body request: AuthRequest): Call<AuthResponse>

    @GET("api/last/login/{username}")
    fun getLastLogin(@Path("username") username: String): Call<LastLoginResponse>

    @GET("api/admin/flag")
    fun getFlag(@Path("flag") flag: String): Call<LastLoginResponse>

}