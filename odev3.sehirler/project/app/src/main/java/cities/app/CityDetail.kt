package cities.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cities.app.adapters.clickLock
import cities.app.classes.City

class CityDetail(private val city: City) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_city_detail, container, false)
        root.rootView.apply {
            val image: ImageView = findViewById(R.id.image)
            val title: TextView = findViewById(R.id.title)
            val population: TextView = findViewById(R.id.population)
            val climate: TextView = findViewById(R.id.climate)
            val geographicInformation: TextView = findViewById(R.id.geographicInformation)

            image.setImageResource(city.image)
            title.text = city.name
            population.text = "Nüfus: ${city.population}"
            climate.text = city.climate
            geographicInformation.text = city.geographicInformation
        }
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        clickLock = false
    }
}