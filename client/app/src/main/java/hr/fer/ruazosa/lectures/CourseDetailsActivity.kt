package hr.fer.ruazosa.lectures

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestFactory
import kotlinx.android.synthetic.main.activity_course_details.*

class CourseDetailsActivity : AppCompatActivity() {

    private var course: Course? = null
    private var courseName: TextView? = null
    private var courseDescription: TextView? = null
    private var teacher: TextView? = null
    private var courseStudentsButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        courseName = findViewById(R.id.courseName) as TextView
        courseDescription = findViewById(R.id.courseDescription) as TextView
        teacher = findViewById(R.id.courseTeacher) as TextView
        courseStudentsButton = findViewById(R.id.courseStudentsButton) as Button

        val shortCourse = intent.getSerializableExtra("course") as ShortCourse

        LoadShortCourseTask().execute(shortCourse)

        courseStudentsButton?.setOnClickListener {
            LoadPersonTask().execute(shortCourse)
        }
    }

    private inner class LoadShortCourseTask: AsyncTask<ShortCourse, Void, Course?>() {

        override fun doInBackground(vararg sCourse: ShortCourse): Course? {
            val rest = RestFactory.instance
            return rest.getCourse(sCourse[0].id)
        }

        override fun onPostExecute(newCourse: Course?) {
            course = newCourse
            courseName?.text = course?.name
            courseDescription?.text = course?.description

            this@CourseDetailsActivity.teacher?.text = course?.teacher?.name
        }
    }


    private inner class LoadPersonTask: AsyncTask<ShortCourse, Void, List<ShortPerson>?>() {
        override fun doInBackground(vararg sCourse: ShortCourse): List<ShortPerson>? {
            val rest = RestFactory.instance

            return rest.getCourseStudents(sCourse[0].id)
        }

        override fun onPostExecute(result: List<ShortPerson>?) {
            updatePersonList(result)
        }
    }

    private fun updatePersonList(person: List<ShortPerson>?) {
        if (person != null) {
            val adapter = PersonAdapter(this, android.R.layout.simple_list_item_1, person)
            peopleListView?.adapter = adapter
        } else {
            // TODO show that people can not be loaded
        }
    }

    private inner class PersonAdapter(context: Context, textViewResourceId: Int, private val shortPersonList: List<ShortPerson>): ArrayAdapter<ShortPerson>(context, textViewResourceId, shortPersonList)

}
