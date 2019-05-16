package hr.fer.tel.ruazosa.lectures.net.urlconnection

import android.util.Log
import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.Person
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestFactory
import hr.fer.tel.ruazosa.lectures.net.RestInterface
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL
import java.util.*

class RestUrlConnection : RestInterface {
    private val baseURL: String = "http://" + RestFactory.BASE_IP + ":8080/api"

    override fun getListOfCourses(): List<ShortCourse>? {
        return try {
            val coursesText = loadData("$baseURL/courses")
            val arrayOfCourses = JSONArray(coursesText)

            val list = LinkedList<ShortCourse>()
            for (i in 0 until arrayOfCourses.length()) {
                val c = parseShortCourse(arrayOfCourses.get(i) as JSONObject)
                list.add(c)
            }

            list
        } catch (e: JSONException) {
            Log.d("RUAZOSA", "problem parsing json", e)
            LinkedList()
        }

    }

    override fun getListOfPersons(): List<ShortPerson>? {
        throw UnsupportedOperationException()
    }

    override fun getCourse(id: Long?): Course? {
        return try {
            val courseText = loadData("$baseURL/courses/$id")
            val jsonCourse = JSONObject(courseText)
            val course = Course()
            course.id = jsonCourse.getLong("id")
            course.name = jsonCourse.getString("name")
            if (!jsonCourse.isNull("teacher"))
                course.teacher = parseShortPerson(jsonCourse.getJSONObject("teacher"))
            course.description = jsonCourse.getString("description")

            course
        } catch (e: JSONException) {
            Log.d("RUAZOSA", "problem parsing json", e)
            null
        }

    }

    override fun getCourseStudents(courseId: Long?): List<ShortPerson> {
        return try {
            val studentsText = loadData("$baseURL/courses/$courseId/students")
            val jsonStudents = JSONArray(studentsText)

            val list = LinkedList<ShortPerson>()
            for (i in 0 until jsonStudents.length()) {
                val jsonStudent = jsonStudents.get(i) as JSONObject
                val student = parseShortPerson(jsonStudent)
                list.add(student)
            }

            list
        } catch (e: JSONException) {
            Log.d("RUAZOSA", "problem parsing json", e)
            LinkedList()
        }

    }

    override fun getPerson(id: Long?): Person {
        throw UnsupportedOperationException()
    }

    override fun enrollPersonToCourse(personId: Long?, courseId: Long?): Boolean {
        throw UnsupportedOperationException()
    }

    override fun disenrollPersonFromCourse(personId: Long?, courseId: Long?): Boolean {
        throw UnsupportedOperationException()
    }

    @Throws(JSONException::class)
    private fun parseShortPerson(jsonPerson: JSONObject): ShortPerson {
        val person = ShortPerson()
        person.id = jsonPerson.getLong("id")
        person.name = jsonPerson.getString("name")
        return person
    }

@Throws(JSONException::class)
private fun parseShortCourse(jsonShortCourse: JSONObject): ShortCourse {
    val course = ShortCourse()
    course.id = jsonShortCourse.getLong("id")
    course.name = jsonShortCourse.getString("name")
    return course
}

    private fun loadData(textURL: String): String? {
        try {
            val url = URL(textURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/json")
            if (connection.responseCode != 200) {
                return "" + connection.responseCode
            }

            val contentStream = connection.inputStream
            val text = readTextFromStream(contentStream)
            connection.disconnect()
            Log.d("RUAZOSA", "učitao sam: $text")
            return text
        } catch (e: MalformedURLException) {
            Log.i("RUAZOSA", "Try to load: $textURL", e)
        } catch (e: ProtocolException) {
            Log.i("RUAZOSA", "Try to load: $textURL", e)
        } catch (e: IOException) {
            Log.i("RUAZOSA", "Try to load: $textURL", e)
        }

        return null
    }

    @Throws(IOException::class)
    private fun readTextFromStream(contentStream: InputStream): String {
        val reader = BufferedReader(
                InputStreamReader(contentStream))

        val sb = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            sb.append(line)
            sb.append("\n")
            line = reader.readLine()
        }

        return sb.toString()
    }

    override fun deletePerson(id: Long?): Boolean? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun postPerson(person: Person?): Boolean? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
