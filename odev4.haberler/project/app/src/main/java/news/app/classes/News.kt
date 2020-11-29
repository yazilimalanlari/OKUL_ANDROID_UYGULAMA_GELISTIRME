package news.app.classes

import android.graphics.Bitmap

data class Source(val name: String)

data class NewsItem(
    val title: String,
    val description: String,
    val urlToImage: String ?= null,
    val source: Source,
    var bitmap: Bitmap ?= null
)

data class News(
    val articles: List<NewsItem>
)