package hc.b4sh0xf.talk

import okhttp3.OkHttpClient
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class RetroFitClient {
    companion object {
        private var INSTANCE : Retrofit ? = null

        fun resetInstance() {
            INSTANCE = null
        }

        private fun getRetroFitInstance(): Retrofit {
            val hostInterceptor = Interceptor { chain ->
                val original = chain.request()
                val newRequest = original.newBuilder()
                    .header("Host", ApiConfig.getDomain())
                    .build()
                chain.proceed(newRequest)
            }

            val http = OkHttpClient.Builder()
                .addInterceptor(hostInterceptor)
                .build()

            if (INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(ApiConfig.getBaseUrl())
                    .client(http)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }

        fun createApiService(): ApiService {
            return getRetroFitInstance().create(ApiService::class.java)
        }
    }
}