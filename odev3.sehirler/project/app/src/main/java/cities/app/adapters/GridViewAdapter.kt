package cities.app.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import cities.app.CityDetail
import cities.app.MainActivity
import cities.app.R
import cities.app.classes.City

var clickLock = false

class GridViewAdapter(
    private val context: Context,
    private val data: ArrayList<City>
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
        val view: View = View.inflate(context, R.layout.city, null)


        val item: City = data[position]
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.image);

        name.text = item.name
        image.setImageResource(item.image)

        (context as MainActivity).apply {
            view.setOnClickListener {
                if (clickLock) return@setOnClickListener
                clickLock = true
                val transaction = supportFragmentManager.beginTransaction()
                val fragment = CityDetail(item)

                transaction.add(R.id.container, fragment)
                transaction.addToBackStack(fragment.javaClass.name)
                transaction.commit()
            }
        }

        return view
    }
}