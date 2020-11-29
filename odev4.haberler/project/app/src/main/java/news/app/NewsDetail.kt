package news.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import news.app.adapters.clickLock
import news.app.classes.NewsItem


class NewsDetail(private val newsDetail: NewsItem) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_news_detail, container, false)
        root.rootView.apply {
            val image: ImageView = findViewById(R.id.image)
            val title: TextView = findViewById(R.id.title)
            val description: TextView = findViewById(R.id.description)
            val source: TextView = findViewById(R.id.source)

            title.text = newsDetail.title
            image.setImageBitmap(newsDetail.bitmap)
            description.text = newsDetail.description
            source.text = newsDetail.source.name
        }
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        clickLock = false
    }
}