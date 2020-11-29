package news.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.GridView
import com.google.gson.Gson
import news.app.classes.News
import news.app.helpers.API
import news.app.settings.*
import okhttp3.*
import okio.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var gridView: GridView
    private lateinit var adapter: news.app.adapters.GridView
    private lateinit var data: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)
        API { data = it }

        Handler().postDelayed({
            adapter = news.app.adapters.GridView(this, data)
            gridView.adapter = adapter
        }, 1000)
    }
}