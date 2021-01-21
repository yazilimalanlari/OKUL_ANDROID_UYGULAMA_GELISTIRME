package english.words

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.room.Room
import english.words.database.AppDatabase
import english.words.database.Word
import org.jsoup.Jsoup
import kotlin.math.abs

class GridViewAdapter(
    private val context: Context,
    private val data: ArrayList<Word>
): BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = TextView(context)
        val word = data[position]
        view.text = "${word.en}: ${word.tr}"
        view.setOnClickListener { _ ->
            (context as MainActivity).addWord(word)
        }
        return view
    }
}

const val CHANNEL_ID = "1000"

class MainActivity : AppCompatActivity() {
    private lateinit var gridView: GridView
    private lateinit var progressBar: ProgressBar
    private lateinit var wordCard: ConstraintLayout
    private lateinit var chooseWord: Button
    private var visibility = false
    private var touchX: Float ?= null
    private var cardTouchX = 0f
    private var screenWidth = 0
    private var screenHeight = 0
    private lateinit var words: MutableList<Word>
    private var activeIndex = 0
    private var wordCardStartX = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)
        progressBar = findViewById(R.id.progressBar)
        wordCard = findViewById(R.id.wordCard)
        chooseWord = findViewById(R.id.chooseWord)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        wordCardStartX = wordCard.x

        words = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "app"
        ).allowMainThreadQueries().build().wordDao().getWords()!!


        if (words.isNotEmpty()) {
            setWord(words[activeIndex])
        } else {
            wordCard.x = screenWidth.toFloat()
        }

        chooseWord.setOnClickListener { view ->
            (view as Button).text = if (visibility) {
                closeWords()
            } else {
                visibility = true
                gridView.visibility = GridView.VISIBLE
                getWordList()
                "Kapat"
            }
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
            .setContentTitle("Hoşgeldin")
            .setContentText("Öğrenmeye Devam Et")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(Math.random().toInt(), builder.build())
        }
    }

    private fun closeWords(): String {
        visibility = false
        gridView.visibility = GridView.INVISIBLE
        return "Örnek Kelimeler"
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (words.isEmpty()) return true
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchX = event.x
                cardTouchX = wordCard.x
            }
            MotionEvent.ACTION_UP -> {
                val db = Room.databaseBuilder(
                    this,
                    AppDatabase::class.java, "app"
                ).allowMainThreadQueries().build()
                val x = wordCard.x + (wordCard.width / 2)
                val word = words[activeIndex]
                if (x < 0) {
                    SpringAnimation(wordCard, DynamicAnimation.TRANSLATION_X, -abs(screenWidth).toFloat()).start()
                    db.wordDao().delete(word.id!!)
                    words.remove(word)

                    when {
                        words.isEmpty() -> {
                            activeIndex = 0
                        }
                        activeIndex < words.size -> {
                            setWord(words[activeIndex])
                        }
                        else -> {
                            activeIndex = 0
                            setWord(words[activeIndex])
                        }
                    }
                } else if (x > screenWidth) {
                    SpringAnimation(wordCard, DynamicAnimation.TRANSLATION_X, screenWidth.toFloat()).start()
                    db.wordDao().changeStatus(word.id!!, 1)
                    word.status = 1

                    if (activeIndex + 1 < words.size) {
                        setWord(words[++activeIndex])
                    } else {
                        activeIndex = 0
                        setWord(words[activeIndex])
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                wordCard.x = event.x - touchX!! + cardTouchX
            }
        }
        return super.onTouchEvent(event)
    }

    private fun getWordList() {
        progressBar.visibility = ProgressBar.VISIBLE
        Thread {
            val url = "https://www.limasollunaci.com/ingilizce-kelimeler-en-cok-kullanilan-1000-kelime-ve-cumle"
            val doc = Jsoup.connect(url).get()
            val data = arrayListOf<Word>()
            for (tr in doc.select("table tr")) {
                val en = tr.select("td:nth-child(2)").text().trim()
                val tr = tr.select("td:last-child").text().trim()
                data.add(Word(tr=tr, en=en))
            }
            runOnUiThread {
                progressBar.visibility = ProgressBar.INVISIBLE
                gridView.adapter = GridViewAdapter(this, data)
            }
        }.start()
    }

    fun addWord(word: Word) {
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "app"
        ).allowMainThreadQueries().build()
        db.wordDao().add(word)
        words.add(word)
        setWord(word)
        chooseWord.text = closeWords()
    }

    private fun setWord(word: Word) {
        wordCard.x = screenWidth.toFloat()
        val word1 = findViewById<TextView>(R.id.word1)
        val word2 = findViewById<TextView>(R.id.word2)
        word1.text = word.en
        word2.text = word.tr
        SpringAnimation(wordCard, DynamicAnimation.TRANSLATION_X, wordCardStartX).start()
    }
}