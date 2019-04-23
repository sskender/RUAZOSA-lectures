package hr.fer.ruazosa.lectures

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.net.RestFactory

class MainActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var loadCourseButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView) as ListView
        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val itemAtPosition = parent.getItemAtPosition(position)
            val shortCourse = itemAtPosition as ShortCourse
            val intent = Intent(this@MainActivity, CourseDetailsActivity::class.java)
            intent.putExtra("course", shortCourse)
            startActivity(intent)
        }

        loadCourseButton = findViewById(R.id.loadCourseButton) as Button
        loadCourseButton?.setOnClickListener {
            LoadCoursesTask().execute()
        }
    }

    private inner class LoadCoursesTask: AsyncTask<Void, Void, List<ShortCourse>?>() {
        override fun doInBackground(vararg params: Void): List<ShortCourse>? {
            val rest = RestFactory.instance

            return rest.getListOfCourses()
        }

        override fun onPostExecute(courses: List<ShortCourse>?) {
            updateCourseList(courses)
        }
    }

    private fun updateCourseList(courses: List<ShortCourse>?) {
        if(courses != null) {
            val adapter = CourseAdapter(this,
                android.R.layout.simple_list_item_1, courses)
            listView?.adapter = adapter
        } else {
            // TODO show that courses can not be loaded
        }
    }

    private inner class CourseAdapter(context: Context, textViewResourceId: Int, private val shortCourseList: List<ShortCourse>) : ArrayAdapter<ShortCourse>(context, textViewResourceId, shortCourseList)

}
