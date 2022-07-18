
# ** USER-SERVICE-ZIP-TEST

**ABOUT**

* User service is an independent microservice which is responsible for all the user related operations like Create User. 
Get User,Get All Users with Pagination.


## Features Implemented.
1. Microservices architecture - User and Account Service created separately as per business functionality.

2. Database per Microservice - Using MySql for User Service and Postgresql for Accounts.

3. Rest Communication - Microservices communication between User and Account Service.

4. Exception Handling -  Controller Advice and exception handler.

5. Docker-Compose - Deployment in one go.

6. Pagination & Sorting - To handle multiple responses from DB in the best way.

7. Spring Profiles - Easily switch between Dev and Docker profile.

8. Caching - Implemented simple EH Cache get User by id.

## TECHNOLOGY USED
1. Core Java
2. Spring boot
3. Junit
4. Gradle
5. Mockito
6. Docker
7. JPA
8. Hibernate
9. MYSQL
10. Open API Documentation


## Set Up Required to run application
1. Docker Engine
2. Spring boot to run locally on tomcat server.

## Profiles 
By default, active profile is dev. If you want to run application on docker just change property
in application.properties to docker i.e spring.profiles.active=docker


## PORTS For User Service.
1. Local - 9090
2. Docker - 8080


## REST METHODS FOR USER

# 1. POST - Create User
URL -  http://localhost:{PORT_NO}/api/v1/users

EXAMPLE(Local)  - http://localhost:9090/api/v1/users
Request Sample :
{
"name": "yo2",
"email": "yo@gmail.com",
"salary": 57687.8,
"expenses": 300.2
}
Response - 201 - CREATED

# 2. GET - Get All Users.
URL -  http://localhost:{PORT_NO}/api/v1/users

EXAMPLE(Local)  - http://localhost:9090/api/v1/users/1

Response - 200 - OK

# 3. GET - Get User by User id.
URL -  http://localhost:{PORT_NO/api/v1/users/{id}

EXAMPLE(Local) - http://localhost:9090/api/v1/users/1

Response - 200 - OK

# 4. GET-Get All Users using Pagination and Sorting
URL -  http://localhost:9090/api/v1/users/pagination/{offset}/{pageSize}/{sortByField}

EXAMPLE(Local)  - http://localhost:9090/api/v1/users/pagination/0/10/salary

Response - 200 - OK



### Unit Testing and Integration Tests For USER
1. Controller Tests are using WebMvcTest for unit testing the code instead of Spring boot tests.
2. Service Tests are using @SpringBootTest to use functionality of Autowiring in Service Layer and to
test the business logic and negative scenarios as well.
3. Integration Tests are using application-test.properties to connect to sql db
and random ports.
4. Unit Testing Code Coverage is > 80%

### SWAGGER URL
Swagger url will be available once application is up.
URL Format will be like : http://localhost:{PORT_NO}}/swagger-ui.html

### Steps To run it on locally
1. First create database schema mentioned in spring.datasource.url in 
application-test.properties for User Service.
2. Build using intellij or any other IDE,
3. Run Main class.
4. Test using POSTMAN or SWAGGER.

###  STEPS TO GET RUNNING ON DOCKER.
NOTE - Please change profile in application.properties file  to docker - spring.profiles.active=docker

1. **BUILD** 
   - Run a gradle build for User from root directory using following command:
                      **gradle build -x test**
   - Build the docker image of User API service using: 
                      **docker build . -t userapi-docker**

2. **DEPLOY**
   * If You want to deploy only User Service independently , run docker-compose.yml in User service root directory using command:
                         **docker-compose up**
 

**Note** - MySql container takes time to come up , given option automatic Restart in docker-compose.yml 
so that User Service will up once my sql is up.

3. **TEST**
   - You can use POSTMAN or Swagger for Testing .
   - First create User from User service using POST mentioned above or check swagger.
   - Check user is created or not by Get User by id.

5. **DESTROY** (Mandatory)
    - Remove all containers after testing using following command:
      **docker-compose down**

**NOTE** - Please follow DEVELOPERS-ACCOUNTS.md file for USER -ACCOUNTS MAPPING using Account Service.





