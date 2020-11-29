package news.app.helpers

import android.os.Handler
import com.google.gson.Gson
import news.app.classes.News
import news.app.settings.API_HOST
import news.app.settings.API_KEY
import news.app.settings.API_PATH_SEGMENTS
import news.app.settings.API_PROTOCOL
import okhttp3.*
import okio.IOException

class API(
    private val callback: (news: News) -> Unit
) {
    private val client = OkHttpClient()

    init {
        val url = HttpUrl.Builder()
            .scheme(API_PROTOCOL)
            .host(API_HOST)
            .addPathSegments(API_PATH_SEGMENTS)
            .addQueryParameter("apiKey", API_KEY)
            .addQueryParameter("country", "TR")
            .build()

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Error $response")

                    val gson = Gson()
                    callback(gson.fromJson(response.body!!.string(), News::class.java))
                }
            }
        })
    }
}