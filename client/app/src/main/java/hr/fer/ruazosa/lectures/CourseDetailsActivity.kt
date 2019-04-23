package hr.fer.ruazosa.lectures

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.net.RestFactory

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
}
