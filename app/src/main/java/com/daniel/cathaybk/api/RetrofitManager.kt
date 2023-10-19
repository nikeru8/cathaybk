package com.daniel.cathaybk.api


import android.util.Log
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitManager {

    companion object {

        private val manager = RetrofitManager()

        var from = ""

        //user API
        fun callHomeFragmentService(from: String): GitHubApi {

            this.from = from
            return manager.gitHubApiService

        }

    }

    class LogJsonInterceptor : Interceptor {
        @Throws(IOException::class)

        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val response: Response = chain.proceed(request)
            val rawJson: String = response.body()!!.string()

            Log.d(
                "",
                String.format("from - $from , RetrofitManager raw JSON response is: %s", rawJson) + ", \n" +
                        "call: ${response.request().url()}"
            )

            // Re-create the response before returning it because body can be read only once
            return response.newBuilder()
                .body(ResponseBody.create(response.body()!!.contentType(), rawJson)).build()
        }
    }

    var mOkHttpClient = OkHttpClient.Builder()
//        .cache(Cache(context.getCacheDir(), cacheSize)) //設置緩存目錄大小
        .addInterceptor(LogJsonInterceptor())
        .connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS) //Connect Time 15s
        .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS) //Read Time 20s
        .writeTimeout(20, java.util.concurrent.TimeUnit.SECONDS) //Write Time 20s
        .build()

    //HomeFragment下方keyword 和 文章資料
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(mOkHttpClient)
        .build()

    //會員資料
    private val gitHubApiService: GitHubApi = retrofit.create(GitHubApi::class.java)

}
