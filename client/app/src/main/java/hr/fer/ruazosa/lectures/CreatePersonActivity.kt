package hr.fer.ruazosa.lectures

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import hr.fer.tel.ruazosa.lectures.entity.Person
import hr.fer.tel.ruazosa.lectures.net.RestFactory
import kotlinx.android.synthetic.main.activity_create_person.*

class CreatePersonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_person)


        // add person button
        addPersonButton.setOnClickListener {

            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val room = roomNumberEditText.text.toString()
            val phone = phoneNumberEditText.text.toString()

            val person = Person(null, firstName, lastName, room, phone)

            PostPersonTask().execute(person)
        }

    }


    // post person task
    private inner class PostPersonTask: AsyncTask<Person?, Void, Boolean?>() {

        override fun doInBackground(vararg params: Person?): Boolean? {
            val rest = RestFactory.instance

            return rest.postPerson(params[0])
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)

            // message
            Toast.makeText(this@CreatePersonActivity, "Person created", Toast.LENGTH_SHORT).show()

            // finish activity
            finish()
        }

    }

}
