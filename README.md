# RUAZOSA-lectures

### Android app using Retrofit ###

### Server setup ###

`
cd client
java -jar lectures-0.0.1-http.jar
`

### REST calls ###

#### Course ####

* GET /api/courses
* GET /api/courses/1
* GET /api/courses/1/students

#### Person ####

* GET /api/persons
* GET /api/persons/2
* DELETE /api/persons/5
* POST /api/persons

`json
{
    "firstName": "Klara",    
    "lastName": "Klarić",    
    "room": "C656",    
    "phone": "12345678"
}
`

#### Enroll and unroll ####

* POST /api/courses/2/enrollPerson/3
* POST /api/courses/2/unenrollPerson/3

### Screenshots ###

![Screenshot1](/screenshots/1.png?raw=true "1")
![Screenshot2](/screenshots/2.png?raw=true "2")
![Screenshot3](/screenshots/3.png?raw=true "3")
![Screenshot4](/screenshots/4.png?raw=true "4")

