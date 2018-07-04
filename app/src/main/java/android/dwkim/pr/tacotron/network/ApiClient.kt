package android.dwkim.pr.tacotron.network

import android.content.Context
import android.dwkim.pr.tacotron.BuildConfig
import com.google.gson.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ApiClient {
    var API_CLIENT: ApiService? = null

    fun buildClients(context: Context){
        val sizeOfCache = (10 * 1024 * 1024).toLong() // 10 MiB
        val cache = Cache(context.cacheDir, sizeOfCache)

        API_CLIENT =
                Retrofit.Builder()
                    .baseUrl(Urls.API_SERVER_BASE)
                    .client(getOkHttpClient(cache))
                    .addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder().create()
                        )
                    )
                    .build()
                    .create(ApiService::class.java)
    }

    private fun getOkHttpClient(cache: Cache): OkHttpClient {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(java.security.cert.CertificateException::class)
                override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String) {

                }

                @Throws(java.security.cert.CertificateException::class)
                override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            return OkHttpClient.Builder().apply {
                sslSocketFactory(
                    SSLContext.getInstance("SSL") .apply {
                        init(null, trustAllCerts, java.security.SecureRandom())
                    }.socketFactory)
                hostnameVerifier { _, _ ->  true }
                cache(cache)
                readTimeout(30, TimeUnit.SECONDS)
                connectTimeout(30, TimeUnit.SECONDS)

                if(BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.HEADERS
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                }
            }.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}