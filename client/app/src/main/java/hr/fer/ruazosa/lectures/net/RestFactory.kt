package hr.fer.tel.ruazosa.lectures.net

import hr.fer.tel.ruazosa.lectures.net.inmemory.InMemoryRestImplementation
import hr.fer.tel.ruazosa.lectures.net.resttemplate.RestSpringTemplate
import hr.fer.tel.ruazosa.lectures.net.retrofit.RestRetrofit
import hr.fer.tel.ruazosa.lectures.net.urlconnection.RestUrlConnection

object RestFactory {
    val BASE_IP = "10.0.2.2"

    val instance: RestInterface
        // MEMORY
        //get() = InMemoryRestImplementation();

        // HTTP
        //get() = RestUrlConnection();
        //get() = RestSpringTemplate();
        get() = RestRetrofit()
}