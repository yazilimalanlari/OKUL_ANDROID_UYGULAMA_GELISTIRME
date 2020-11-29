package news.app.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.IOException
import java.net.URL

class DownloadImageTask(private val callback: (Bitmap) -> Unit) : AsyncTask<URL, Void, Bitmap>() {
    override fun doInBackground(vararg params: URL?): Bitmap? {
        val url = params[0]
        try {
            val inputStream = URL(url.toString()).openStream()
            return BitmapFactory.decodeStream(inputStream)
        } catch(e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: Bitmap) {
        callback(result)
    }
}