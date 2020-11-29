package news.app.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import news.app.MainActivity
import news.app.NewsDetail
import news.app.R
import news.app.classes.News
import news.app.classes.NewsItem
import news.app.helpers.DownloadImageTask
import java.net.URL

var clickLock = false

class GridView(
    private val context: Context,
    private val data: News
): BaseAdapter() {
    override fun getCount(): Int {
        return data.articles.size
    }

    override fun getItem(position: Int): Any {
        return data.articles[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.news_item, null)

        val item: NewsItem = data.articles[position]
        val title: TextView = view.findViewById(R.id.title)
        val image: ImageView = view.findViewById(R.id.image);

        title.text = item.title

        if (item.urlToImage != null) {
            DownloadImageTask() { bitmap ->
                image.setImageBitmap(bitmap)
                item.bitmap = bitmap
            }.execute(URL(item.urlToImage))
        }


        (context as MainActivity).apply {
            view.setOnClickListener {
                if (clickLock) return@setOnClickListener
                clickLock = true
                val transaction = supportFragmentManager.beginTransaction()
                val fragment = NewsDetail(item)

                transaction.add(R.id.container, fragment)
                transaction.addToBackStack(fragment.javaClass.name)
                transaction.commit()
            }
        }

            return view
    }
}
