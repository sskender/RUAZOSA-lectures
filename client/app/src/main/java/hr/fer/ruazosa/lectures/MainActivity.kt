package hr.fer.ruazosa.lectures

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var loadCourseButton: Button? = null

    // toggle what will adapter do based on which items are loaded
    private var nowIsLoadCoursesButtonClicked: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // list view (on tap)
        listView = findViewById(R.id.listView) as ListView
        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->

            if (nowIsLoadCoursesButtonClicked) {
                // do this for courses
                val itemAtPosition = parent.getItemAtPosition(position)
                val shortCourse = itemAtPosition as ShortCourse

                val intent = Intent(this@MainActivity, CourseDetailsActivity::class.java)
                intent.putExtra("course", shortCourse)

                startActivity(intent)
            } else {
                // do this for persons
                val itemAtPosition = parent.getItemAtPosition(position)
                val shortPerson = itemAtPosition as ShortPerson

                DeletePersonTask().execute(shortPerson)
            }

        }


        // load courses button
        loadCourseButton = findViewById(R.id.loadCourseButton) as Button

        loadCourseButton?.setOnClickListener {
            nowIsLoadCoursesButtonClicked = true
            LoadCoursesTask().execute()
        }


        // load person button
        loadPersonsbutton.setOnClickListener {
            nowIsLoadCoursesButtonClicked = false
            LoadPersonsTask().execute()
        }
    }



    // load courses task
    private inner class LoadCoursesTask: AsyncTask<Void, Void, List<ShortCourse>?>() {

        override fun doInBackground(vararg params: Void): List<ShortCourse>? {
            val rest = RestFactory.instance

            return rest.getListOfCourses()
        }

        override fun onPostExecute(courses: List<ShortCourse>?) {
            updateCourseList(courses)
        }

        private fun updateCourseList(courses: List<ShortCourse>?) {
            if (courses != null) {
                val adapter = CourseAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1, courses
                )
                listView?.adapter = adapter
            } else {
                // TODO show that courses can not be loaded
            }
        }

    }



    // load persons task
    private inner class LoadPersonsTask : AsyncTask<Void, Void, List<ShortPerson>?>() {

        override fun doInBackground(vararg params: Void?): List<ShortPerson>? {
            val rest = RestFactory.instance

            return rest.getListOfPersons()
        }

        override fun onPostExecute(result: List<ShortPerson>?) {
            updatePersonsList(result)
        }

        private fun updatePersonsList(persons: List<ShortPerson>?) {
            if (persons != null) {
                val adapter = PersonsAdapter(this@MainActivity, android.R.layout.simple_list_item_1, persons)
                listView?.adapter = adapter
            } else {
                // TODO show that courses can not be loaded
            }
        }

    }



    // delete person task
    private inner class DeletePersonTask : AsyncTask<ShortPerson, Void, Boolean?>() {

        override fun doInBackground(vararg params: ShortPerson?): Boolean? {
            val rest = RestFactory.instance

            return rest.deletePerson(params[0]?.id)
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)

            // refresh list after element deletion
            LoadPersonsTask().execute()

            Toast.makeText(this@MainActivity, "Person deleted", Toast.LENGTH_SHORT).show()
        }

    }



    // adapters

    // courses adapter
    private inner class CourseAdapter(
        context: Context,
        textViewResourceId: Int,
        private val shortCourseList: List<ShortCourse>
    ) : ArrayAdapter<ShortCourse>(context, textViewResourceId, shortCourseList)


    // persons adapter
    private inner class PersonsAdapter(
        context: Context,
        textViewResourceId: Int,
        private val shortPersonsList: List<ShortPerson>
    ) : ArrayAdapter<ShortPerson>(context, textViewResourceId, shortPersonsList)

}
