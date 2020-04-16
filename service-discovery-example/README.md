# Enterprise Employer Data Management API

* The project exposes the APIs for managing the master data for employers for example their employees etc.
* The project can be built in a docker container and can be deployed to various environments
* When the container starts the postgres database is created, under the employer_api database, employer schema is created.
* The application comes up with the YAML configuration file for configuring a few values in it.
* The application uses consul and valut for configuration, it can be enabled for running the application across different environments for different values. 


## Prerequisite
* Install Docker, I used version = 18.03.1-ce
* Download STS to import and do further development on it
* Install Maven, I used version = 3.5.3
* Build and run project on MAC or Unix based system 
* Start consul, vault and postgres database.

## Import

* Unzip the project source code zip file.
* In spring source tool IDE, click - File -> New -> "Java Project".
* Refer to the root directory of you project to import it as a new project
* Right click on the project in the IDE, go to "Configure" -> "Convert to maven project"
* Right click on the project in the IDE, go to "Maven" to update the project

## Testcases

* There test cases that are the pure unit test cases.
* There are test cases that are the integrtion testcases.
* Unit testcases run under the default profile and the integration testcases run under 'integration-tests' profile. 
* Integration testcases use an in memory instance of h2 database to verify the end to end api call.


## Build

* Execute the command from the application root directory - 'mvn clean package' command to build the project.
* run 'mvn clean package' to build and run the unit test cases
* run 'mvn clean install -Pintegration-tests' to build and run the integration tests only

## Docker image

* The application is composed in a docker image.
* Also the consul and vault run in a docker container.


## Start the container

* Execute the command from the following application root directory.
 'docker-compose up --build -d'. After running the application starts at the port 8888 and with the context root /employer/api

## Service registry

* Once the service is up, browse the consul server at http://localhost:8500, go to the services tab and make sure the service is registered under the 'Services' tab with the name 'employer-api' and the health check is also passing.

## End points to test

* GET /employer/api/v1/employees/list (http://localhost:8888/employer/api/v1/employees/list)
* POST /employer/api/v1/employees (http://localhost:8888/employer/api/v1/employees)
* GET /employer/api/v1/departments (http://localhost:8888/employer/api/v1/departments)

## Stop the application

* Execute the following command from the application root directory
 'docker-compose down'

## Exception

* The exception handling mechanism has been set up
* NoDataFoundException is a custom runtime exception created this returns 404 if the data is not present in the tables.


## API testing using Swagger

* /employer/api/swagger-ui.html# to check and execute the end points available. (e.g. http://localhost:8888/employer/api/swagger-ui.html#)

## Sonar code analysis (Optional)

* Build the project first
* Start the sonar server (I used version 7.8)
* Command for running sonar reports

 mvn sonar:sonar \
  -Dsonar.projectKey=emdm \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.sources=src/main/java \
  -Dsonar.tests=src/test/java \
  -Dsonar.java.binaries=target/classes \
  -Dsonar.java.test.binaries=target/test-classes \
  -Dsonar.java.libraries=target \
  -Dsonar.login=6aa87237be17a252198d84b6225605951b2e9dc1
  
  You would need to use your sonar project key and token

## Enhancements/Possible improvements

* Swagger configuration values can also be externalized.
* The backend validations should be enabled if this service is going to be shared by other clients also.
* Caching can be enabled in the project
* More documentation can be added to the application