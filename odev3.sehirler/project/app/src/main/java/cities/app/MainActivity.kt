package cities.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import cities.app.adapters.GridViewAdapter
import cities.app.classes.City
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val cities: ArrayList<City> = ArrayList()
    private lateinit var gridView: GridView
    private lateinit var adapter: GridViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val json = JSONArray(readFile("Cities.json"))

        for(i in 0 until json.length()) {
            val city = json.getJSONObject(i)
            cities.add(City(
                city.getString("name"),
                resources.getIdentifier(city.getString("image"), "drawable", packageName),
                city.getString("population"),
                city.getString("climate"),
                city.getString("geographicInformation")
            ))
        }

        gridView = findViewById(R.id.gridView)
        adapter = GridViewAdapter(this, cities)

        gridView.adapter = adapter
    }

    private fun readFile(fileName: String): String {
        assets.open(fileName).bufferedReader().use { return it.readText() }
    }
}